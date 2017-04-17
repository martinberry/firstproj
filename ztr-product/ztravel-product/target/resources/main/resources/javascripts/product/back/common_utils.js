/**
 * 判断字符串非空
 * @param str
 * @returns
 */
function strNB(str){
	if(str === undefined || str == null || $.trim(str) == ''){
		return false;
	}else{
		return true;
	}
}

/**
 * 判断字符串空
 * @param str
 * @returns
 */
function strB(str){
	return !strNB(str);
}

/**
 * 获取字符串长度（中文一个字是两个字符）
 * @param str
 * @returns
 */
function getStrLength(str) { 
    var cArr = str.match(/[^\x00-\xff]/ig); 
    return str.length + (cArr == null ? 0 : cArr.length); 
} 