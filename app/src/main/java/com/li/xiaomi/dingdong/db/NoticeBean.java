package com.li.xiaomi.dingdong.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/6/7
 * 内容：
 * 最后修改：
 */
@Entity
public class NoticeBean implements Parcelable {

    @Id
    Long NoticeId;//打卡id
    Long ClockTime;//打卡时间(时间戳)
    String NoticeContent;//打卡记录
    Boolean Result;//打卡记录
    Integer ClockId;//闹钟id
    @Generated(hash = 491049956)
    public NoticeBean(Long NoticeId, Long ClockTime, String NoticeContent,
            Boolean Result, Integer ClockId) {
        this.NoticeId = NoticeId;
        this.ClockTime = ClockTime;
        this.NoticeContent = NoticeContent;
        this.Result = Result;
        this.ClockId = ClockId;
    }
    @Generated(hash = 303198189)
    public NoticeBean() {
    }
    public Long getNoticeId() {
        return this.NoticeId;
    }
    public void setNoticeId(Long NoticeId) {
        this.NoticeId = NoticeId;
    }
    public Long getClockTime() {
        return this.ClockTime;
    }
    public void setClockTime(Long ClockTime) {
        this.ClockTime = ClockTime;
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
    public Integer getClockId() {
        return this.ClockId;
    }
    public void setClockId(Integer ClockId) {
        this.ClockId = ClockId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.NoticeId);
        dest.writeValue(this.ClockTime);
        dest.writeString(this.NoticeContent);
        dest.writeValue(this.Result);
        dest.writeValue(this.ClockId);
    }

    protected NoticeBean(Parcel in) {
        this.NoticeId = (Long) in.readValue(Long.class.getClassLoader());
        this.ClockTime = (Long) in.readValue(Long.class.getClassLoader());
        this.NoticeContent = in.readString();
        this.Result = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ClockId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<NoticeBean> CREATOR = new Parcelable.Creator<NoticeBean>() {
        @Override
        public NoticeBean createFromParcel(Parcel source) {
            return new NoticeBean(source);
        }

        @Override
        public NoticeBean[] newArray(int size) {
            return new NoticeBean[size];
        }
    };
}
