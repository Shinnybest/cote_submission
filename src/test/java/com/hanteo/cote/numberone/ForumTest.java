package com.hanteo.cote.numberone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForumTest {

    public static final String GENDER_M = "남자";
    private static final String GENDER_F = "여자";

    private static final String GROUP_EXO = "엑소";
    private static final String GROUP_BTS = "방탄소년단";
    private static final String GROUP_BLACKPINK = "블랙핑크";

    private Forum forum;

    @BeforeEach
    void setUp() {
        forum = new Forum();

        forum.addBoard(null, GENDER_M, null);
        forum.addBoard(null, GENDER_F, null);

        forum.addBoard(null, GROUP_EXO, GENDER_M);
        forum.addBoard(null, GROUP_BTS, GENDER_M);
        forum.addBoard(null, GROUP_BLACKPINK, GENDER_F);

        forum.addBoard(2, "첸", GROUP_EXO);
        forum.addBoard(3, "백현", GROUP_EXO);
        forum.addBoard(4, "시우민", GROUP_EXO);
        forum.addBoard(7, "뷔", GROUP_BTS);
        forum.addBoard(9, "로제", GROUP_BLACKPINK);

        forum.addNotice(1, GROUP_EXO);
        forum.addNotice(5, GROUP_BTS);
        forum.addNotice(8, GROUP_BLACKPINK);

        forum.addAnonymous(6);
    }

    @Test
    @DisplayName("익명게시판 검색")
    void searchAnonymous() {
        String result = forum.searchByName("익명게시판");
        assertNotNull(result);
        assertTrue(result.contains("\"name\":\"익명게시판\""));
        assertTrue(result.contains("\"id\":6"));
    }

    @Test
    @DisplayName("공지사항 검색")
    void searchNotice() {
        String resultExoNotice = forum.searchByName("공지사항");
        assertNotNull(resultExoNotice);
        assertTrue(resultExoNotice.contains("\"id\":1"));
        assertTrue(resultExoNotice.contains("\"id\":5"));
        assertTrue(resultExoNotice.contains("\"id\":8"));
    }

    @Test
    @DisplayName("성별 검색")
    void searchGender() {
        String result = forum.searchByName(GENDER_M);
        assertNotNull(result);
        assertTrue(result.contains("\"name\":\"남자\""));
        assertTrue(result.contains("\"name\":\"엑소\""));
        assertTrue(result.contains("\"name\":\"방탄소년단\""));
        assertTrue(result.contains("\"name\":\"시우민\""));
        assertTrue(result.contains("\"name\":\"뷔\""));
        assertFalse(result.contains("\"name\":\"블랙핑크\""));
    }

    @Test
    @DisplayName("그룹 검색")
    void searchGroup() {
        String result = forum.searchByName(GROUP_EXO);
        assertNotNull(result);
        assertTrue(result.contains("\"name\":\"엑소\""));
        assertTrue(result.contains("\"name\":\"첸\""));
        assertTrue(result.contains("\"name\":\"백현\""));
        assertTrue(result.contains("\"name\":\"시우민\""));
        assertTrue(result.contains("\"name\":\"공지사항\""));
    }

    @Test
    @DisplayName("멤버 검색")
    void searchMember() {
        String result = forum.searchByName("로제");
        assertNotNull(result);
        assertTrue(result.contains("\"name\":\"로제\""));
        assertTrue(result.contains("\"id\":9"));
        assertFalse(result.contains("\"name\":\"뷔\""));
    }

    @Test
    @DisplayName("유효한 ID 검색")
    void searchValidId() {
        String result = forum.searchById(1);
        assertNotNull(result);
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"공지사항\""));
    }

    @Test
    @DisplayName("존재하지 않는 ID 검색")
    void searchInvalidId() {
        String result = forum.searchById(100);
        assertEquals("{}", result);
    }
}