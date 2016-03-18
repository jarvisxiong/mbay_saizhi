package org.sz.mbay.traffic.util;




public class ChargeOrderStatus {
	
	public static final ChargeOrderStatus PAYMENTS_DUE=new ChargeOrderStatus(0,"未付款");
	public static final ChargeOrderStatus RECHARGEING=new ChargeOrderStatus(1,"已下发");
	public static final ChargeOrderStatus RECHARGE_SUCCESS=new ChargeOrderStatus(2,"充值成功");
	public static final ChargeOrderStatus RECHARGE_FAILED=new ChargeOrderStatus(3,"充值失败");
	public static final ChargeOrderStatus RECHARGE_CANCLED=new ChargeOrderStatus(4,"交易关闭");
	
	private  int value;
	private  String name;

	public ChargeOrderStatus(int value, String typename) {
		this.value = value;
		this.name = typename;
	}
	
   public ChargeOrderStatus() {}
	
	
    public static ChargeOrderStatus valueOf(int value){
    	switch(value){
    	case 0:return PAYMENTS_DUE;
    	case 1:return RECHARGEING;
    	case 2:return RECHARGE_SUCCESS;
    	case 3:return RECHARGE_FAILED;
    	case 4:return RECHARGE_CANCLED;
    	}
    	//throw new Exception("");
    	return null;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		this.name=this.valueOf(value).getName();
	}

	public String getName() {
		return name;
	}

	public void setTypename(String typename) {
		this.name = typename;
	}
    
	

	@Override
	public boolean equals(Object obj) {
		return (this.value == ((ChargeOrderStatus)obj).value);
	}
    
	


}
