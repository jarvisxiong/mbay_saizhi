package org.sz.mbay.traffic.util;

public final class Area {

	private Area(int key, String name) {
		this.value = key;
		this.name = name;
	}

	private Area() {
	}

	private int value;

	private String name;

	public static final Area QUANGUO = new Area(0, "全国");
	public static final Area BEIJING = new Area(11, "北京");
	public static final Area TIANJIN = new Area(12, "天津");
	public static final Area HEBEI = new Area(13, "河北");
	public static final Area SX_JIN = new Area(14, "山西");
	public static final Area NEIMENGGU = new Area(15, "内蒙古");
	public static final Area LIAONING = new Area(21, "辽宁");
	public static final Area JILIN = new Area(22, "吉林");
	public static final Area HEILONGJIANG = new Area(23, "黑龙江");
	public static final Area SHANGHAI = new Area(31, "上海");
	public static final Area JIANGSU = new Area(32, "江苏");
	public static final Area ZHEJIANG = new Area(33, "浙江");
	public static final Area ANHUI = new Area(34, "安徽");
	public static final Area FUJIAN = new Area(35, "福建");
	public static final Area JIANGXI = new Area(36, "江西");
	public static final Area SHANDONG = new Area(37, "山东");
	public static final Area HENAN = new Area(41, "河南");
	public static final Area HUBEI = new Area(42, "湖北");
	public static final Area HUNAN = new Area(43, "湖南");
	public static final Area GUANGDONG = new Area(44, "广东");
	public static final Area GUANGXI = new Area(45, "广西");
	public static final Area HAINAN = new Area(46, "海南");
	public static final Area CHONGQING = new Area(50, "重庆");
	public static final Area SICHUAN = new Area(51, "四川");
	public static final Area GUIZHOU = new Area(52, "贵州");
	public static final Area YUNNAN = new Area(53, "云南");
	public static final Area XIZANG = new Area(54, "西藏");
	public static final Area SX_QIN = new Area(61, "陕西");
	public static final Area GANSU = new Area(62, "甘肃");
	public static final Area QINGHAI = new Area(63, "青海");
	public static final Area NINGXIA = new Area(64, "宁夏");
	public static final Area XINJIANG = new Area(65, "新疆");

	public String getName() {
		return this.name;
	}

	public String toString() {
		return String.valueOf(this.value);
	}

	public static Area getArea(int value) {
		switch (value) {
		case 0:
			return QUANGUO;
		case 11:
			return BEIJING;

		case 12:
			return TIANJIN;

		case 13:
			return HEBEI;

		case 14:
			return SX_JIN;

		case 15:
			return NEIMENGGU;

		case 21:
			return LIAONING;

		case 22:
			return JILIN;

		case 23:
			return HEILONGJIANG;

		case 31:
			return SHANGHAI;

		case 32:
			return JIANGSU;

		case 33:
			return ZHEJIANG;

		case 34:
			return ANHUI;

		case 35:
			return FUJIAN;

		case 36:
			return JIANGXI;

		case 37:
			return SHANDONG;

		case 41:
			return HENAN;

		case 42:
			return HUBEI;

		case 43:
			return HUNAN;

		case 44:
			return GUANGDONG;

		case 45:
			return GUANGXI;

		case 46:
			return HAINAN;

		case 50:
			return CHONGQING;

		case 51:
			return SICHUAN;

		case 52:
			return GUIZHOU;

		case 53:
			return YUNNAN;

		case 54:
			return XIZANG;

		case 61:
			return SX_QIN;

		case 62:
			return GANSU;

		case 63:
			return QINGHAI;

		case 64:
			return NINGXIA;
		case 65:
			return XINJIANG;
		default:
			return QUANGUO;
		}
	}

}
