package org.sz.mbay.statistics.event;

/**
 * html元素点击
 * 
 * @author jerry
 */
public class ElementClick extends BaseEvent {
	
	public ElementClick() {
		super("002");
	}
	
	// 元素类型
	private ElementType eleType;
	
	// 按钮、超链接等元素的名称
	private String name;
	
	public ElementType getEleType() {
		return eleType;
	}
	
	public void setEleType(ElementType eleType) {
		this.eleType = eleType;
	}
	
	public String getName() {
		return getString(name);
	}
	
	public void setName(String name) {
		this.name = setString(name);
	}
	
	@Override
	public String toString() {
		String str = null;
		switch (eleType) {
			case BUTTON:
				str = "点击了【" + getName() + "】按钮，页面url【" + getUrl() + "】";
				break;
			case LINK:
				str = "点击了【" + getName() + "】链接，页面url【" + getUrl() + "】";
				break;
			default:
			case OTHER:
				str = "点击了【" + getName() + "】元素，页面url【" + getUrl() + "】";
		}
		return str;
	}
	
}
