package com.hanteo.cote.numberone;

import com.google.gson.Gson;

import java.util.*;

public class Forum {

    private final Map<String, Board> boards;
    private final List<Board> notices;
    private Board anonymous;

    public Forum() {
        this.boards = new HashMap<>();
        this.notices = new ArrayList<>();
    }

    public void addBoard(Integer id, String name, String parentName) {
        Board board = new Board(id, name);
        boards.put(name, board);

        if (parentName != null) {
            Board parentBoard = boards.get(parentName);
            if (parentBoard != null) {
                parentBoard.addSubCategory(board);
            }
        }
    }

    public void addNotice(Integer id, String parentName) {
        Board notice = new Board(id, "공지사항");
        notices.add(notice);
        if (parentName != null) {
            Board parentBoard = boards.get(parentName);
            if (parentBoard != null) {
                parentBoard.addSubCategory(notice);
            }
        }
    }

    public void addAnonymous(Integer id) {
        this.anonymous = new Board(id, "익명게시판");
    }

    public String searchById(Integer id) {
        ArrayList<Board> boardList = new ArrayList<>();
        boardList.addAll(boards.values());
        boardList.addAll(notices);
        boardList.add(anonymous);

        Board board = null;

        for (Board found : boardList) {
            if (found.getId() != null && found.getId().equals(id)) {
                board = found;
            }
        }
        return toJson(board);
    }

    public String searchByName(String keyword) {
        if (keyword.equals("공지사항")) {
            return toJson(notices);
        } else if (keyword.equals("익명게시판")) {
            return toJson(anonymous);
        }
        Board board = boards.get(keyword);
        return toJson(board);
    }

    private String toJson(Board board) {
        if (board == null) {
            return "{}";
        }
        Gson gson = new Gson();
        return gson.toJson(board);
    }

    private String toJson(List<Board> boards) {
        if (boards.size() == 0) {
            return "{}";
        }
        Gson gson = new Gson();
        return gson.toJson(boards);
    }

    public static class Board {
        private final Integer id;
        private final String name;
        private final List<Board> subCategories;

        public Board(Integer id, String name) {
            this.id = id;
            this.name = name;
            this.subCategories = new ArrayList<>();
        }

        public void addSubCategory(Board board) {
            this.subCategories.add(board);
        }

        public Integer getId() {
            return id;
        }
    }
}
