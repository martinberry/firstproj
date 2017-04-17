//  判断点击范围是否超出元素
function isOuterTrigger(event, $ele) {
	if($ele.length == 0){
		return false ;
	}
    var top = $ele.offset().top,
        left = $ele.offset().left,
        width = $ele.outerWidth(),
        height = $ele.outerHeight();

    var _x = event.pageX,
        _y = event.pageY;

    if (_y > top + height || _y < top || _x > left + width || _x < left) {
        return false;
    } else {
        return true;
    }
}