/**
==========================================
===   btn_pc.js pc端按钮功能处理 2014-10-19
===	  2014-11-10 聊过的online不自动连接了
==========================================
*/


(function() {
	//导入相关"包"
	var Ani = MECHAT.Ani;
	var Dom = MECHAT.Dom;
	var Ajax = MECHAT.Ajax;
	var Lang = MECHAT.Lang;
	var Color = MECHAT.Color;
	var Event = MECHAT.Event;
	var Cookie = MECHAT.Cookie;
	var Broswer = MECHAT.Broswer;
	/**
	====================================================================================
	* Attr 相关公共属性 要统一提出来，现在没时间，就这样写2014.10.26
	====================================================================================
	*/
	var Attr = {
		//相关路径的配置
		chatHtml : _CONFIG.version == 'local' ? './mechat_pc.html' : (_CONFIG.chatHost + '/mechat_pc.html'),
		visitHtml : _CONFIG.version == 'local' ? './mechat_visit.html' : (_CONFIG.visitHost + '/mechat_visit.html'),
		button : _CONFIG.statHost + '/unit/button',
		imgPath : _CONFIG.sHost + '/images/',
        metadata: _CONFIG.chatHost + '/set/attrs', //metadata数据的传输
		/*----------------------分割线----------------------------*/
		source : 'PC',
		unitid : _CONFIG.unitid,
		cookieKey : 'MECHAT_CKID',//存放cookie的key
		cookieVal : '',
		visitVal : 'YES', // [NOT 不进行visit连接 ， YES进行连接]
		cid : 0,
		lp : window.location.href,
		title : document.title || '',
		referrer : document.referrer || '',
		dialPage : window.location.href || '',
		fromPage : Broswer.mc_getReferrer() || '',
		//颜色值
		colorId : 'Initial',
		company : {
			uname : '',
			utel : '',
			uhead : '' ,//客服的头像
			ulogo : '' //公司的logo
		},//公司相关信息，在online 没分配客服前 显示的信息
		//button相关
		chatcnt : '',//由button里面返回 visit/poll 发送 
		style : 2,//[1:左下角，2:右下角，3:无按钮]
		status : 'show',//show 加载chat,hide 
		online_txt : '在线咨询',
		leave_txt : '给我们留言',
		lvmsg_txt : '',//留言页面的文案
		lvmsgChecks : {
			ismail : "true",
			ismsn : "true",
			isqq : "true",
			istel : "true"
		},//留言联系方式
		isReady : false , //标识，与page页面是否握手成功
		isRecMeta : false, //标识是否可接收metadata数据
		postMsgArr : [] ,//消息队列纪录
		btnClick : 'MECHAT-BTNCLICK',//标识是否点击了按钮，以便其他窗口同步打开
		timeMarkKey : 'MECHAT-TIMEMARK',
		isChatOpen : false,//标识窗口是否打开
		diffOnline : 10000 ,//进行自动online的时间间隔(毫秒单位)
		hasOnlineUServer : true ,//是否有客服在线的标识
		priDomain : Broswer.getPriDomain(),//获取当前的主域名
		setCookie : function(key,value){// cookie的统一设置
			var domain = this.priDomain;
			var exp = new Date();
			exp.setTime(exp.getTime() + 10800000);//存放3个小时
			if(domain.indexOf(".") > 0){
				Cookie.set(key,value,exp,'/',domain);
			}else{
				Cookie.set(key,value,exp);
			}
		},
		getPath: function() {
            return window.location.protocol + '//' + window.location.host + window.location.pathname;
        }
	};

	/**
	====================================================================================
	* Btn 相关
	====================================================================================
	*/
	var Btn = {
		css : "#MECHAT-PCBTN{font:16px/24px 'Helvetica Neue', Helvetica, Arial;position:fixed;bottom:-100%;background:#1abc9c;color:#fff;text-align:center;padding:10px 15px 8px;border-top-left-radius:5px;border-top-right-radius:5px;cursor:pointer;z-index:2147483645;}#MECHAT-PCBTN span{display:inline-block;height:24px;padding:0 0 0 34px;background:url('"+Attr.imgPath+"pc_btn_icon.png') 0 0 no-repeat}#MECHAT-LAYOUT{position:fixed;right:50px;bottom:-100%;width:300px;height:430px;z-index:2147483647; overflow:hidden;border:1px solid #1abc9c;border-top-left-radius:5px;border-top-right-radius:5px;box-shadow:0 0 3px 2px rgba(135,135,135,0.1)}#MECHAT-PCBTN,#MECHAT-LAYOUT{_position:absolute;_bottom:auto;_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)))}",
		html : '<div id="MECHAT-LAYOUT"><iframe src="" style="width:100%;height:100%;border:0;" frameborder="no" scrolling="no"></iframe></div><div id="MECHAT-PCBTN"><span class="MECHAT-BTNLEAVE" style="color:#fff;">在线咨询</span></div><iframe src="" style="display:none;" id="MECHAT_VISIT"></iframe>'
	};
	/**
	====================================================================================
	* Tel 通信相关
	====================================================================================
	*/ 
	var Tel = {
		//unit/button
		button : function(callback){
			var json = {
				cookie :Attr.cookieVal,
				lp : Attr.lp,
				id : Attr.unitid,
				unitid : Attr.unitid,
				title: Attr.title,//页面标题
				localr : Attr.referrer,
				source: Attr.source,
				fp: Attr.fromPage
			}
			json['lvt'] = Cookie.read('MECHAT_LVTime') || '';  //last visit time
			Attr.setCookie('MECHAT_LVTime',new Date().getTime());
			Ajax.jsonp(Attr.button,json,'mechat_btncb');
			window.mechat_btncb = function(d){
				callback(d);
			}
		},

		metadata: function(data) {
            if (data != null) {
                data['unitid'] = Attr.unitid;
                data['cookie'] = Attr.cookieVal;
                data['source'] = Attr.source;
                Ajax.jsonp(Attr.metadata, data, 'metadataCallback');
                window.metadataCallback = function(d){};
            }
        }
	};

	/**
	====================================================================================
    * metadata数据模型对象
	====================================================================================

    */
    var MetadataModel = {
        'realName': '',
        'sex': '',
        'birthday': '',
        'age': '',
        'job': '',
        'avatar': '',
        'comment': '',
        'appUserId': '',
        'appUserName': '',
        'appNickName': '',
        'tel': '',
        'email': '',
        'address': '',
        'QQ': '',
        'weibo': '',
        'weixin': '',
        'appkey': '',
        'from': '',
        'extraParams': '',
        'deviceBrand': '',
        'deviceModel': '',
        'os': '',
        'osVersion': '',
        'resolution': '',
        'networkEnvironment': '',
        'deviceToken': '',
        'appVersion': '',
        'osLanguage': '',
        'osTimezone': '',
        /**
         * MetadatModel 和 origin都有的属性，组装成一个新对象返回
         */
        filterProperty: function(origin) {
            var obj = null;
            for (var v in origin) {
                if (this.hasOwnProperty(v) && origin[v]) {
                    obj = obj == null ? {} : obj;
                    if (v == 'extraParams' && typeof origin[v] == 'object') {
                        if (typeof JSON != 'undefined') {
                            obj['extraParams'] = JSON.stringify(origin[v]);
                        }
                    } else {
                        obj[v] = origin[v];
                    }
                }
            }
            return obj;
        }
    };
	/**
	====================================================================================
	* TimeMarker 时间检测
	====================================================================================
	*/ 
	var TimeMarker = {
		save : function(){
			//存入最新的时间
			Attr.setCookie(Attr.timeMarkKey,new Date().getTime());
		},
		read : function(){
			var time = Cookie.read(Attr.timeMarkKey);
			return time;
		},
		//判断是否在单位时间内
		isUnitTime : function(){
			var time = this.read();
			if(time){
				time = parseInt(time);
				var t = new Date().getTime();
				if(Math.abs(t - time) < 11000){
					return true;
				}
			}
			return false;
		},
		//刷新记录的时间
		refreshMark : function(){
			var self = this;
			self.save();
			if(self.markTimeout){clearTimeout(self.markTimeout);}
			self.markTimeout = setTimeout(function(){
				self.refreshMark();
			},2000);
		}
	};
	/**
	====================================================================================
	* 聊天窗口的状态设置
	*	窗口状态改变就
	====================================================================================
	*/ 
	var ChatStatus = {
		isStart : false,
		statusKey : 'MECHAT-CHATSTATUS',
		setStatus : function(){
			Attr.setCookie(this.statusKey,Attr.isChatOpen);
		},
		read : function(){
			var status = Cookie.read(this.statusKey);
			return status;
		},
		/**
		* 状态的更新
		*/
		refreshStatus : function(){
			var self = this;
			var status = self.read();
			if(Attr.isChatOpen && status == "false"){
				self.setStatus();
			}
			self.statusTimeout = setTimeout(function(){
				self.refreshStatus();
			},3000);
		}
	};

	/**
	====================================================================================
	* view 展示的控制
	====================================================================================
	*/ 
	var View = function(){
		var self = new Object();
		self.isPtMsg = false;//标识是否给iframe发过消息了
		self.isVisit = false;//标识是否进行访客通信
		var isLoadVisit = false;//标识是否加载了visit页面
		var metaData = null;
		//初始化
		self.init = function(){
			self.create();
			//获取到节点对象
			self.btn = Dom.get("#MECHAT-PCBTN");
			self.btnHeight = self.btn.offsetHeight;
			self.btnWidth = self.btn.offsetWidth;
			self.btnIcon = Dom.get("span",self.btn)[0];
			self.btnIcon.innerHTML = Attr.online_txt;//设置按钮文字

			self.layout = Dom.get("#MECHAT-LAYOUT");
			self.layoutHeight = self.layout.offsetHeight;
			self.layoutWidth = self.layout.offsetWidth;
			Dom.hide(self.layout);
			self.chatFrame = Dom.get("iframe",self.layout)[0];
			self.visitFrame = Dom.get("#MECHAT_VISIT");

		}
		//创建节点
		self.create = function(){
			Dom.loadStyle(Btn.css);//载入css
			//载入html节点
			var el = document.createElement('div');
			el.innerHTML = Btn.html;
			document.body.appendChild(el);
		}
		//访客页面的加载
		self.loadVisit = function(){
			if(Attr.visitVal == "NOT"){return;}
			self.isVisit = true;
			isLoadVisit = true;
			var url =  Attr.visitHtml + 
						"?unitid=" + Attr.unitid + "&cookie=" + Attr.cookieVal + "&cid=" + Attr.cid + "&dev=" + Attr.source + "&cn=" + Attr.chatcnt;
			self.visitFrame.src = url;
		}
		//聊天页面的加载
		self.loadChat = function(){
			var url = Attr.chatHtml + 
						"?unitid=" + Attr.unitid + "&cookie=" + Attr.cookieVal + "&cid=" + Attr.cid + 
						"&color=" + Attr.colorId;
			if(Attr.visitVal == "NOT"){//不进行visit时，chat窗口自动连接 、IE6 自动连接
				url += "&ol=auto";
			}
			self.chatFrame.src = url;
		}
		/**
		* 统一存放cookie 和visitValue
		*/
		self.cookieSave = function(){
			Attr.setCookie(Attr.cookieKey,{cookieVal : Attr.cookieVal});
		}
		/**
		* 统一读取
		*/
		self.cookieRead = function(){
			var val = Cookie.readObj(Attr.cookieKey);
			if(val.cookieVal){
				Attr.cookieVal = val.cookieVal;
			}
		}
		/**
		* 给chat窗口发消息
		*/
		self.msgToChat = function(msg){
			msg['lvmsg_txt'] = Attr.lvmsg_txt,//留言页面的文案
			msg['ismail'] = Attr.lvmsgChecks.ismail;
			msg['ismsn'] = Attr.lvmsgChecks.ismsn;
			msg['isqq'] = Attr.lvmsgChecks.isqq;
			msg['istel'] = Attr.lvmsgChecks.istel;
			msg['cookie'] = Attr.cookieVal;
			msg['dialPage']	= Attr.dialPage;
			msg['hasOnlineUServer'] = Attr.hasOnlineUServer;

			msg = Lang.objToStr(msg);
			//消息加入队列中
			Attr.postMsgArr.push(msg);
			//消息传递
			self.postMsg();
			//存入visit的状态,标识不进行连接
			Attr.visitVal = "NOT";
		}
		/**
		* 传递握手消息
		*/
		self.msgTohands = function(){
			var msg = {
				mechat:"MECHAT-CHAT-HANNDS",
				dialPage : Attr.dialPage
			}
			msg = Lang.objToStr(msg);
			self.postMsg2Frame(msg);
		}
		/**
		* 消息的传递
		*/
		self.postMsg = function(){
			if(Attr.isReady){
				if(Attr.postMsgArr.length > 0){
					var msg;
					for(var i = 0,len = Attr.postMsgArr.length; i < len; i++){
						msg = Attr.postMsgArr[i];
						self.postMsg2Frame(msg);
					}
					//清空列队
					Attr.postMsgArr = new Array();
				}
			}
		}
		/**
		* 传递metadata数据
		*/
		// self.pushMetaData = function(data){
		// 	if(data && typeof data == 'object'){
		// 		if(!metaData){metaData = {};}
		// 		for(var key in data){
		// 			if(key == 'extraParams' && typeof data[key] == 'object'){
		// 				if(typeof JSON != 'undefined'){
		// 					metaData['extraParams'] = JSON.stringify(data[key]);
		// 				}
		// 			}else{
		// 				metaData[key] = data[key];
		// 			}
		// 		}
		// 	}
		// }
		// self.postMetadata = function(data){
		// 	self.pushMetaData(data);
		// 	if(metaData && Attr.isRecMeta){
		// 		metaData['mechat'] = "MECHAT-METADATA";
		// 		metaData = Lang.objToStr(metaData);
		// 		self.postMsg2Frame(metaData);
		// 		metaData = null;
		// 	}
		// }
		/**
		* 消息传递到frame中
		*/
		self.postMsg2Frame = function(msg){
			if(isLoadVisit && self.visitFrame.contentWindow.postMessage){
				self.visitFrame.contentWindow.postMessage(msg,"*");
			}
			if(self.chatFrame.contentWindow.postMessage){
				self.chatFrame.contentWindow.postMessage(msg,"*");
			}
		}
		/**
		* 设置按钮的样式
		*/
		self.buttonStyle = function(){
			//1、设置按钮颜色
			self.setColor();
			//2、根据style设置button位置
			self.setLeft();
			self.btnIcon.innerHTML = Attr.online_txt;//设置按钮文字
		}
		/**
		* 设置按钮 左下方的位置
		*/
		self.setLeft = function(){
			if(Attr.style == 1){
				Dom.css(self.btn,"left", "100px");
				Dom.css(self.layout,"left", "50px");
			}else if(Attr.style == 2){
				Dom.css(self.btn,"right", "100px");
				Dom.css(self.layout,"right", "50px");
			}else{
				Dom.hide(self.btn);
			}
		}
		/**
		* 设置按钮的颜色
		*/
		self.setColor = function(){
			var c = Color.get(Attr.colorId);
			if(c){
				Dom.css(self.btn ,"background",c[0]);
				Dom.css(self.layout,'border','1px solid ' + c[0]);
				Dom.css(self.layout,'border-bottom','none');
				//给btn添加hover效果
				Event.on(self.btn,'mouseover',function(){
					Dom.css(self.btn,"background",c[1]);
				});
				Event.on(self.btn,'mouseout',function(){	
					Dom.css(self.btn,"background",c[0]);				
				});
			}
		}
		/**
		* 设置留言按钮
		*/
		self.setLeaveBtn = function(){
			self.btnIcon.innerHTML = Attr.leave_txt;
			Dom.css(self.btnIcon,"background-position","0 -27px");
		}
		/**
		* 设置在线按钮
		*/
		self.setOnlineBtn = function(){
			self.btnIcon.innerHTML = Attr.online_txt;
			Dom.css(self.btnIcon,"background-position","0 0");
		}
		/**
		* btn的显示
		*/
		self.btnShow = function(){
			if(Attr.style == 3){return;}
			Ani.move(self.btn,{bottom:0,b:-self.btnHeight});
		}
		/**
		* btn的隐藏
		*/
		self.btnHide = function(){
			if(Attr.style == 3){return;}
			Ani.move(self.btn,{bottom:-self.btnHeight,b:0});
		}
		/**
		* 是否是猎豹浏览器
		*/
		self.isLBBROWSER = function(){
			var ua = navigator.userAgent;
			return (ua.indexOf("LBBROWSER") > 0 || ua.indexOf("Chrome/34") > 0 || ua.indexOf("Chrome/35") > 0 
				|| ua.indexOf('MetaSr') > 0);
		}
		/**
		* 聊天窗口的显示
		*/
		self.layoutShow = function(){
			//窗口状态记录
			Attr.isChatOpen = true;
			ChatStatus.setStatus();

			self.btnHide();
			if(self.isLBBROWSER()){
				Dom.css(self.layout,"bottom","0px");
				Dom.show(self.layout);
			}else{
				Dom.show(self.layout);
				Ani.move(self.layout,{bottom:0,b:-self.layoutHeight});
			}
			//消息的发送
			if(!self.isPtMsg){
				self.msgToChat({
					mechat:"MECHAT-CHAT-SHOW",
					//button返回的公司信息，在online 没链接到客服时显示
					//将公司信息作为客服，显示
					usname : Attr.company.uname,//将公司名字，作为客服名字，显示
					ustel : Attr.company.utel || '',
					usavatar : Attr.company.ulogo || ''
				});
				self.isVisit = false;//已经自动连接了，就拒绝访客消息
				self.isPtMsg = true;
				//保存相关信息
				self.cookieSave();
				//刷新标记时间
				TimeMarker.refreshMark();
			}else{				
				self.msgToChat({mechat:"MECHAT-LAYOUT-SHOW"});
			}
		}
		/**
		* 聊天窗口的隐藏
		*/
		self.layoutHide = function(){
			//窗口状态记录
			Attr.isChatOpen = false;
			ChatStatus.setStatus();

			Ani.move(self.layout,{bottom:-self.layoutHeight,b:0},60,function() {
				Dom.hide(self.layout);
				self.btnShow();
			});
		}
		return self;
	};
	/**
	====================================================================================
	* Contr 逻辑的相关控制
	====================================================================================
	*/ 
	var Contr = function(){
		var self = new Object();
		/*------------- 分割线 ---------------*/
		var view = new View();
		self.isChatShow = false;//标识Chat窗口是否显示
		self.getView = function(){
			return view;
		}

		/**
         * Metadata的发送
         */
        self.sendMetadata = function(data) {
            if (data && typeof data == 'object') {
                var meta = MetadataModel.filterProperty(data);
                Tel.metadata(meta);
            }
        };

		/**
		* 初始化
		*/
		self.init = function(){

			//获取指定的用户编号
			if(typeof getMeChatPartnerUserID == 'function'){
				var partnerUser = getMeChatPartnerUserID();
				if(typeof partnerUser == 'object' && partnerUser != null && partnerUser.hasOwnProperty('userId')){
					var userId = partnerUser.userId;
					if(userId && userId != 'undefined'){
						Attr.cookieVal = Lang.html_encode(Lang.trim(userId));
					}
				}
			}

			view.init();
			//获取到Cookie
			if(!Attr.cookieVal){
				view.cookieRead();
			}
			//IE6不进行visit访问
			if(MECHAT.Broswer.get() == MECHAT.Broswer.IE6){
				Attr.visitVal = "NOT";
			}
			//单位时间内，直接连接
			if(TimeMarker.isUnitTime()){
				Attr.visitVal = "NOT";
				TimeMarker.refreshMark();//时间的更新
				ChatStatus.refreshStatus();
			}
			//执行unit/button
			self.button();
			self.readClick();
			/* -------------- 设置事件 ------------ */
			Event.on(view.btn,"click",function(){
				self.btnClick();
			});
			/**
			* 外用调用
			*/
			window.mechatClick = function(callback){
				self.btnClick();
				if(typeof callback == "function"){
					callback();
				}
			}
			/**
			* 外部调用传输metadata数据
			*/
			// window.mechatMetadata = function(data){
				// view.postMetadata(data);
			// }
		}
		/**
		* unit/button
		*/
		self.button = function(){
			Tel.button(function(data){
				if(data.success){
					//设置相关属性
					Attr.cid = data.cid ||'';
					Attr.cookieVal = data.cookie||'';
					view.cookieSave();// 将cookieVal存放到Cookie中
					//读取Button相关配置数据
					self.readBtnData(data);
					//资源加载
					if(Attr.status == "show" || Attr.status == "true"){
						view.loadVisit();//加载Visit
						view.loadChat();//加载mechat_pc
						view.buttonStyle();//按钮样式
						view.btnShow();//显示按钮

						//设置按钮为无客服的状态
						if(!Attr.hasOnlineUServer){
							self.userver();
						}
						//判断是非要自动打开 （制定单位时间内）
						// if(TimeMarker.isUnitTime() && ChatStatus.read() == "true"){
						// 	self.btnClick();
						// }
					}
					
					//重置链接
                	self.resetHref();

					/**
					* 外部调用传输metadata数据
					*/
					window.mechatMetadata = function(data){
						self.sendMetadata(data);
					}
				}
			});
		};
		/**
		* 列队消息的
		*/
		self.postMsg = function(){
			view.postMsg();	
		}
		/**
		*  握手消息
		*/
		self.msgTohands = function(){
			view.msgTohands();
		}
		/**
		* 读取返回的button 数据
		*/
		self.readBtnData = function(data){
			Attr.chatcnt = data.chatCnt || Attr.chatcnt;

			Attr.style = data.pc_ubutton.style || Attr.style;//按钮样式
			if(_CONFIG.btn && _CONFIG.btn == "hide"){
				Attr.style = 3;
			}
			Attr.status = data.pc_ubutton.status || Attr.status;//状态
			Attr.colorId = data.pc_ubutton.color||Attr.colorId;//获取颜色值

			Attr.online_txt = data.pc_ubutton.online || Attr.online_txt;
			Attr.leave_txt = data.pc_ubutton.leave || Attr.leave_txt;

			//公司相关信息
			Attr.company.uname = data.uname;
			Attr.company.utel = data.utel;
			Attr.company.ulogo = data.pc_ubutton.unitLogo;
			//留言文字
			Attr.lvmsg_txt = data.lvmsgNote || '';
			Attr.lvmsgChecks = data.lvmsgChecks || Attr.lvmsgChecks;
			//获得是否有客服在线
			if(typeof data.unitHasOnlineUServer != undefined){
				if(data.unitHasOnlineUServer == "false" || data.unitHasOnlineUServer == false){
					Attr.hasOnlineUServer = false;
					Attr.visitVal = "NOT";
				}
			}
		};
		/**
		* 访客的消息
		* @param picurl 图片的链接
		*/
		self.visitMsg = function(name,head,txt,tel,picurl){
			view.isPtMsg = true;//visit打开的窗口，就不再发送OPEN 消息
			self.btnHide();//显示窗口
			view.msgToChat({
				mechat : 'MECHAT_VISIT_MSG',
				content : txt,
				//这里要改成，发消息那个人的信息
				usname : name,
				ustel : tel ||'',
				picurl : picurl || ''
			}); 
			//刷新标记时间
			TimeMarker.refreshMark();
		}
		/**
		* 按钮的显示
		*/
		self.btnShow = function(){
			if(!self.isChatShow){return;}
			view.layoutHide();
			self.isChatShow = false;
		}
		/**
		* 按钮的隐藏
		*/
		self.btnHide = function(){
			if(self.isChatShow){return;}
			view.layoutShow();
			self.isChatShow = true;
		}
		/**
		* 按钮的点击处理
		*/
		self.btnClick = function(){
			self.saveClick();//存入点击的消息
			if(self.isChatShow){
				self.btnShow();
			}else{
				self.btnHide();
			}
		}
		/**
		* 无客服的处理
		*/
		self.userver = function(){
			view.setLeaveBtn();
		}
		/**
		* 保存点击的消息
		*/
		self.saveClick = function(){
			// if(!view.isPtMsg){
			// 	//存入点击按钮的消息
			// 	var exp = new Date();
			// 	exp.setTime(exp.getTime() + 10 * 1000);//存放10秒 
			// 	// 使用打开页面unit/button cookie的值
			// 	view.cookieSave();
			// 	Cookie.set(Attr.btnClick,Attr.btnClick,exp);

			// }
		}
		/**
		* 轮询是否有窗口打开
			2014-11-07每个窗口只有点击过后才online，不用刻意控制一起上线
		*/
		self.readClick = function(){
			// if(view.isPtMsg){return;}
			// var click =	Cookie.read(Attr.btnClick);
			// if(click && click == Attr.btnClick){
			// 	view.cookieRead();
			// 	self.btnHide();
			// 	return;
			// }
			// setTimeout(self.readClick,1500);
		}
		/*------------- 分割线 ---------------*/

		/*-------------- 华丽的分割线 ----------------*/

        self.setHref = function(aTag, href) {
            if (!aTag || !href) {
                return;
            }

            var querys = href.split('?')[1];
            var queryObj = Broswer.parseQuerystring(querys);

            var qpart1 = '&_realref_=' + encodeURIComponent(Attr.getPath());
            var qpart2 = 'cookie=' + Attr.cookieVal;
            var link = Attr.chatHtml + '?unitid=' + Attr.unitid + "&win=max&ol=auto&color=" + Attr.colorId + "&";
            for (var i in queryObj) {
            	if(i != 'unitid' && i != 'win' && i != 'ol' && i != 'color'){
                	link = link + i + '=' + queryObj[i] + '&';
            	}
            }
            link = link + qpart2 + qpart1;
            aTag.setAttribute('href', link);
        };

        self.resetHref = function() {
            var aTags = document.getElementsByTagName('a');
            for (var i = 0, len = aTags.length; i < len; i++) {
                var href = aTags[i].getAttribute('href');
                if (href && (href.indexOf('meiqia.com/mechat_pc.html') >= 0 || href.indexOf('meiqia.com/chat/') >= 0 || href.indexOf('mobilechat/chat/') >= 0 || href.indexOf('mechat.im/chat/') >= 0 || href.indexOf('mechatim.com/chat/') >= 0)) {
                    self.setHref(aTags[i], href);
                }
            }
        };


		return self;
	};
	/**
	====================================================================================
	* 启动执行
	====================================================================================
	*/ 
	(function(){
		if(!Attr.unitid || Attr.unitid == undefined || Attr.unitid == "undefined" || Attr.unitid == "null"){
			return false;
		}
		var contr = new Contr();
		contr.init();
		/**
		* 窗口消息监听
		*/
		MECHAT.Event.on(window,"message",function(e){
			var data = Lang.strToObj(e.data);
			if(typeof data == "string"){
				switch(data){
					case "MECHAT-OPEN":
						contr.btnHide();
					break;
					case "MECHAT-CLOSE":
						contr.btnShow();
					break;
					case "MECHAT-USERVER":
						contr.userver();
					break;
					case "MECHAT-READY"://页面加载完的回馈
						//查看消息列表中否有消息
						Attr.isReady = true;
						contr.msgTohands();//传递握手消息
						contr.postMsg();//发送队列中的消息
						if(TimeMarker.isUnitTime() && ChatStatus.read() == "true"){
							contr.btnClick();
						}
					break;
					case "MECHAT-METADATA"://可开始接收metadata的数据
						// Attr.isRecMeta = true;
						// contr.getView().postMetadata();
					break;
				}
			}else if(typeof data == "object" && data["mechat"]){
				switch(data["mechat"]){
					case "MECHAT-VISIT-INVITE"://邀请消息
						if(contr.getView().isVisit){
							contr.visitMsg(data['fromName'],data['usavatar'],data['content'],data['ustel'],data['picUrl']);//发送邀请消息
						}
					break;
				}
			}
		});
	})();
})();