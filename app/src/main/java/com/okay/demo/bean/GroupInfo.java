package com.okay.demo.bean;

public class GroupInfo {
    //组号
    private int mGroupID;
    // Header 的 title
    private String mTitle;
    private int position;
    // 组的成员个数
    private int mGroupLength;
    int[] aaa;

    public GroupInfo(int groupId, String title) {
        this.mGroupID = groupId;
        this.mTitle = title;
    }

    public void setGroupLength(int groupLength) {
        this.mGroupLength = groupLength;
    }

    public boolean isLastViewInGroup () {
        return position == mGroupLength - 1 && position >= 0;
    }

    public int getGroupID() {
        return mGroupID;
    }

    public void setGroupID(int groupID) {
        this.mGroupID = groupID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }


    public boolean isFirstViewInGroup () {
        return position == 0;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}