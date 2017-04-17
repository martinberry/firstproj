/**
  *>创建密码: 
  *	密码框内自动填充请输入8-28位密码；填写密码时自动填充文字消失；
  *	字符数少于8，则提示：密码需输入8~28个字符哦(即时确认)
  *	密码字符限定：
  *	1.        大小写字母
  *	2.        数字
  *	3.字符串：  ~!@#$%^&*
  *	密码安全判断规则：
  *	输入密码后，光标移开文本框内，系统判定密码安全程度：
  *	>弱  仅包含“纯数字/纯大写字母/纯小写字母/纯字符”中一种格式  字符大于8
  *	
  *	>中  包含“纯数字/纯大写字母/纯小写字母/纯字符”中任意两种格式  字符大于8
  *	
  *	>强  包含“纯数字/纯大写字母/纯小写字母/纯字符”中任意三种或三种以上格式  字符大于8
  *	
  *	点击眼睛图标，明文显示密码；
  *
  */

var isPassword = /^([a-zA-Z0-9~!@#$%^&*]{8,28})$/ ;

var password_hint_lengtherror = '密码需要8~28个字符哦' ;

var password_hint_error = '密码为8-28位数字、字母或常用字符组合' ;

var password_hint_error2 = '两次输入密码不一致' ;

/*
 * wrong : -1
 * weak : 0
 * normal : 1
 * strong : 2
 */
function checkPassword(pwd){
	var lv = 0;
	if(!isPassword.test(pwd)){
		return -1 ;
	}else{
		if(pwd.match(/[a-z]/g)){lv++;}
		if(pwd.match(/[A-Z]/g)){lv++;}
		if(pwd.match(/[0-9]/g)){lv++;}
		if(pwd.match(/[~!@#$%^&*]/g)){lv++;}
		if(lv > 3){lv=3;} 
		return lv - 1 ;
	}
}




