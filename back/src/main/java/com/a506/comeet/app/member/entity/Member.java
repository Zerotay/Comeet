package com.a506.comeet.app.member.entity;

import com.a506.comeet.app.etc.entity.Til;
import com.a506.comeet.common.enums.MemberFeature;
import com.a506.comeet.app.keyword.entity.MemberKeyword;
import com.a506.comeet.app.member.controller.dto.MemberUpdateRequestDto;
import com.a506.comeet.common.BaseEntityWithSoftDelete;
import com.a506.comeet.app.room.entity.RoomMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

/**
 * 미완성
 */

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
//@EqualsAndHashCode(of="memberId")
public class Member extends BaseEntityWithSoftDelete implements UserDetails {

    @Id
    @Column(name="member_id", updatable = false, unique = true, nullable = false)
    private String memberId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Builder.Default
    private String link = "default_link_letsgo";
    @Column(name = "profile_image")
    @Builder.Default
    private String profileImage = "default_profile_image_letsgo";
    @Column(nullable = false)
    private String email;
    @Builder.Default
    private String description = "default_description_letsgo";

    @Enumerated(EnumType.STRING)
    private MemberFeature feature = MemberFeature.EARTH;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberKeyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Til> tils = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public void updateMember(MemberUpdateRequestDto dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.link = dto.getLink();
        this.profileImage = dto.getProfileImage();
        this.email = dto.getEmail();
        this.description = dto.getDescription();
        this.feature = dto.getFeature();
    }

    public void addRoomMember(RoomMember roomMember){
        roomMembers.add(roomMember);
    }

    public void removeRoomMember(RoomMember roomMember){
        roomMembers.removeIf(rm -> rm.getMember().getMemberId().equals(roomMember.getMember().memberId) && rm.getRoom().getId().equals(roomMember.getRoom().getId()));
    }

    public void delete(){
        deleteSoftly();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.memberId; // nickname으로 해야하나? (중복 안되는 값이면 괜찮다고 함)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}