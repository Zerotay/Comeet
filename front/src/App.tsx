import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import tw from "tailwind-styled-components";
import { TailwindComponent } from "tailwind-styled-components/dist/tailwind";
import { NavBar } from "./components/NavBar";
import { RoomList } from "./pages/RoomList";
import { Mainpage } from "./pages/Mainpage";
import { Community } from "./pages/Community";
import { Mypage } from "./pages/Mypage";
import { LoginPage } from "./pages/LoginPage";
import { SignupPage } from "./pages/SignupPage";
import { Room } from "./pages/Room";
import RoomChannel from "./components/RoomChannel";
import NewRoom from "./pages/NewRoom";
import ConditionCheck from "./pages/ConditionCheck";
import TextEditor from "./components/TextEditor";
import { RecruitBoardList } from "./pages/RecruitBoardList";
import { FreeBoardList } from "./pages/FreeBoardList";
import { BoardDetail } from "./pages/BoardDetail";

function App() {
  //임시
  const isLogin = true;
  const [isModal, setIsModal] = React.useState<boolean>(false);

  const modalHandler = () => {
    setIsModal(!isModal);
  };

  return (
    <div className="App h-dvh">
      <BrowserRouter>
        <NavBarContainer>
          <NavBar />
        </NavBarContainer>
        <RoutesContainer>
          <Routes>
            <Route path="/" element={<Mainpage />}></Route>
            {isLogin ? (
              <>
                <Route path="/roomlist" element={<RoomList />}></Route>

                {/* 커뮤니티인데 곧 삭제 예정... */}
                <Route path="/community" element={<Community />}></Route>

                {/* 모집 게시판 */}
                <Route
                  path="/recruit-board"
                  element={<RecruitBoardList />}
                ></Route>

                {/* 모집게시판 글 상세보기 */}
                <Route
                  path="/recruit-board/:boardId"
                  element={<BoardDetail />}
                ></Route>

                {/* 자유 게시판 */}
                <Route path="/free-board" element={<FreeBoardList />}></Route>

                {/* 자유게시판 글 상세보기 */}
                <Route
                  path="/free-board/:boardId"
                  element={<BoardDetail />}
                ></Route>

                {/* 마이페이지 */}
                <Route path="/mypage" element={<Mypage />}></Route>

                <Route path="/room/:roomId/*" element={<Room />}></Route>
              </>
            ) : (
              <>
                <Route path="/login" element={<LoginPage />}></Route>
                <Route path="/signup" element={<SignupPage />}></Route>
              </>
            )}
            <Route path="/new-room" element={<NewRoom />}></Route>
            <Route path="/before-entrance" element={<ConditionCheck />}></Route>
            <Route path="/temp" element={<TextEditor />}></Route>
          </Routes>
        </RoutesContainer>
      </BrowserRouter>
    </div>
  );
}

const NavBarContainer = tw.div`
  h-12
`;

const RoutesContainer = tw.div`
`;

export default App;
