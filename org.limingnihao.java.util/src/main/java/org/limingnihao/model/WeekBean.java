package org.limingnihao.model;

import org.limingnihao.util.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekBean implements Serializable {

	private int weekId;
	private Date startDate;
	private Date endDate;
    private String weekName;

	public String toString() {
		return "WeekBean - weekId=" + weekId + ", startDate=" + DateUtil.format(startDate) + ", endDate" + DateUtil.format(endDate);
	}

	public String format(String formatString, String joint) {
		String result = "";
		if (this.startDate != null && this.endDate != null) {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			result = format.format(this.startDate) + joint + format.format(this.endDate);
		}
		return result;
	}

	public int getWeekId() {
		return weekId;
	}

	public void setWeekId(int weekId) {
		this.weekId = weekId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }
}
