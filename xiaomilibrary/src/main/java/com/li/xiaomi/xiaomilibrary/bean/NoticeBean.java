package com.li.xiaomi.xiaomilibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.li.xiaomi.xiaomilibrary.ui.dialog.bottomList.MiListInterface;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */
@Entity
public class NoticeBean implements MiListInterface, Parcelable {

    @Id
    Long NoticeId;//打卡id
    Long NoticeTitle;//打卡时间(时间戳)
    String NoticeContent;//打卡记录
    Boolean Result;//打卡记录


    @Generated(hash = 135754508)
    public NoticeBean(Long NoticeId, Long NoticeTitle, String NoticeContent,
            Boolean Result) {
        this.NoticeId = NoticeId;
        this.NoticeTitle = NoticeTitle;
        this.NoticeContent = NoticeContent;
        this.Result = Result;
    }

    @Generated(hash = 303198189)
    public NoticeBean() {
    }


    protected NoticeBean(Parcel in) {
        if (in.readByte() == 0) {
            NoticeId = null;
        } else {
            NoticeId = in.readLong();
        }
        if (in.readByte() == 0) {
            NoticeTitle = null;
        } else {
            NoticeTitle = in.readLong();
        }
        NoticeContent = in.readString();
        byte tmpResult = in.readByte();
        Result = tmpResult == 0 ? null : tmpResult == 1;
    }

    public static final Creator<NoticeBean> CREATOR = new Creator<NoticeBean>() {
        @Override
        public NoticeBean createFromParcel(Parcel in) {
            return new NoticeBean(in);
        }

        @Override
        public NoticeBean[] newArray(int size) {
            return new NoticeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (NoticeId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(NoticeId);
        }
        if (NoticeTitle == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(NoticeTitle);
        }
        dest.writeString(NoticeContent);
        dest.writeByte((byte) (Result == null ? 0 : Result ? 1 : 2));
    }

    @Override
    public String getString() {
        return null;
    }

    public Long getNoticeId() {
        return this.NoticeId;
    }

    public void setNoticeId(Long NoticeId) {
        this.NoticeId = NoticeId;
    }

    public Long getNoticeTitle() {
        return this.NoticeTitle;
    }

    public void setNoticeTitle(Long NoticeTitle) {
        this.NoticeTitle = NoticeTitle;
    }

    public String getNoticeContent() {
        return this.NoticeContent;
    }

    public void setNoticeContent(String NoticeContent) {
        this.NoticeContent = NoticeContent;
    }

    public Boolean getResult() {
        return this.Result;
    }

    public void setResult(Boolean Result) {
        this.Result = Result;
    }
}
