package org.sz.mbay.base.area;

public final class Area {

	public static final Area QUANGUO = new Area(0);

	public Area() {
	}

	public Area(int key) {
		this.value = key;
	}

	public int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		this.name = this.getName();
	}

	public static Area valueOf(int value) {
		Area area = new Area(value);
		area.name = area.getName();
		return area;
	}

	public String getName() {
		switch (this.value) {
		case 0:
			return "全国";
		case 11:
			return "北京";
		case 12:
			return "天津";
		case 13:
			return "河北";
		case 14:
			return "山西";
		case 15:
			return "内蒙古";
		case 21:
			return "辽宁";
		case 22:
			return "吉林";
		case 23:
			return "黑龙江";
		case 31:
			return "上海";
		case 32:
			return "江苏";
		case 33:
			return "浙江";
		case 34:
			return "安徽";
		case 35:
			return "福建";
		case 36:
			return "江西";
		case 37:
			return "山东";
		case 41:
			return "河南";
		case 42:
			return "湖北";
		case 43:
			return "湖南";
		case 44:
			return "广东";
		case 45:
			return "广西";
		case 46:
			return "海南";
		case 50:
			return "重庆";
		case 51:
			return "四川";
		case 52:
			return "贵州";
		case 53:
			return "云南";
		case 54:
			return "西藏";
		case 61:
			return "陕西";
		case 62:
			return "甘肃";
		case 63:
			return "青海";
		case 64:
			return "宁夏";
		case 65:
			return "新疆";
		}
		return name;
	}

	public String name;

}
