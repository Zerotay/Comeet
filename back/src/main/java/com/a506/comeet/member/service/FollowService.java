package com.a506.comeet.member.service;

import com.a506.comeet.member.controller.FollowRequestDto;
import com.a506.comeet.member.controller.FollowerRequestDto;
import com.a506.comeet.member.controller.FollowingReqeustDto;
import com.a506.comeet.member.controller.UnfollowRequestDto;
import com.a506.comeet.member.controller.dto.MemberSimpleResponseDto;
import com.a506.comeet.member.entity.Follow;
import com.a506.comeet.member.entity.Member;
import com.a506.comeet.member.repository.FollowRepository;
import com.a506.comeet.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    public String follow(FollowRequestDto req, String fromId) {
        if (req.getMemberId().equals(fromId)) return null;
        Member from = memberRepository.findByMemberIdAndIsDeletedFalse(fromId).orElseGet(null);
        Member to = memberRepository.findByMemberIdAndIsDeletedFalse(req.getMemberId()).orElseGet(null);
        Follow created = followRepository.save(new Follow(from, to));
        return created.getTo().getMemberId();
    }

    public boolean unfollow(UnfollowRequestDto req, String fromId){
        if (req.getMemberId().equals(fromId)) return false;
        Member from = memberRepository.findByMemberIdAndIsDeletedFalse(fromId).orElseGet(null);
        Member to = memberRepository.findByMemberIdAndIsDeletedFalse(req.getMemberId()).orElseGet(null);
        Follow find = followRepository.findByFromAndTo(from, to).get();
        followRepository.delete(find);
        return true;
    }

    public Slice<MemberSimpleResponseDto> getFollower(FollowerRequestDto req, String memberId){
        return followRepository.getFollowers(PageRequest.of(req.getPageNo(), req.getPageSize()), memberId);
    }

    public Slice<MemberSimpleResponseDto> getFollowing(FollowingReqeustDto req, String memberId){
        return followRepository.getFollowings(PageRequest.of(req.getPageNo(), req.getPageSize()), memberId);
    }
}