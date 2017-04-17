/**
 * 小数精度
 * @param value
 * @param precision
 * @returns
 */
function toFixed(precision){
	var value = this;
	if(isNaN(value)){
		return value;
	}
	precision = precision || 0;
	var array = String(value).split(".");
	var integral = array[0];
	var fraction = array[1] || "";
	if(fraction.length > precision){
		if(fraction.charAt(precision) >= '5'){
			fraction = (fraction.substr(0, precision) * 1 + 1) + "";
		}else{
			fraction = fraction.substr(0, precision);
		}
	}else{
		fraction = paddingRight(fraction, precision, '0')
	}
	if(fraction * 1 >= Math.pow(10, precision)){
		integral = (integral * 1 + (integral < 0 ? -1 : 1)) + "";
		fraction = fraction.substr(fraction.length - precision, precision);
	}
	return integral + (precision == 0 ? "" : ("." + paddingLeft(fraction, precision, '0')));
}

function paddingRight(val, length, char){
	val = String(val || "");
	if(val.length >= length){
		return val.substr(0, length);
	}
	return val + new Array(length - val.length + 1).join(char);
}

function paddingLeft(val, length, char){
	val = String(val || "");
	if(val.length >= length){
		return val.substr(0, length);
	}
	return new Array(length - val.length + 1).join(char) + val;
}

/**
 * 加法
 */
function add(num){
	var array1 = this.toString().split(".");
	var array2 = num.toString().split(".");
	var f1 = (array1[1] || "").length;
	var f2 = (array2[1] || "").length;
	var base = Math.max(f1, f2);
	var num1 = (array1[0] + paddingRight(array1[1], base, '0')) * 1;
	var num2 = (array2[0] + paddingRight(array2[1], base, '0')) * 1;
	return (num1 + num2) / Math.pow(10, base);
}

/**
 * 减法
 */
function subtract(num){
	return this.add(-num);
}

/**
 * 乘法
 */
function multiply(num){
	var array1 = this.toString().split(".");
	var array2 = num.toString().split(".");
	var f1 = (array1[1] || "").length;
	var f2 = (array2[1] || "").length;
	var base = f1 + f2;
	num1 = (array1[0] + (array1[1]||"")) * 1;
	num2 = (array2[0] + (array2[1]||"")) * 1;
	return (num1 * num2) / Math.pow(10, base);
}

/**
 * 除法
 */
function divide(num){
	var array1 = this.toString().split(".");
	var array2 = num.toString().split(".");
	var f1 = (array1[1] || "").length;
	var f2 = (array2[1] || "").length;
	var base = Math.max(f1 + f2);
	num1 = (array1[0] + paddingRight(array1[1], base, '0')) * 1;
	num2 = (array2[0] + paddingRight(array2[1], base, '0')) * 1;
	return num1 / num2;
}

/**
 * 求余
 */
function mod(num){
	var array1 = this.toString().split(".");
	var array2 = num.toString().split(".");
	var f1 = (array1[1] || "").length;
	var f2 = (array2[1] || "").length;
	var base = Math.max(f1 + f2);
	num1 = (array1[0] + paddingRight(array1[1], base, '0')) * 1;
	num2 = (array2[0] + paddingRight(array2[1], base, '0')) * 1;
	var quotient = num1 / num2;
	if(quotient == parseInt(quotient)){
		return 0;
	}
	return (num1 - num2 * quotient.toString().split(".")[0]) / Math.pow(10, base);
}

/**
 * string TOFIXED方法，用于兼容原先的数字TOFIXED
 */
function strToFixed(precision){
	var value=this;
	if(isNaN(value)){
		return;
	}
	return new Number(value).toFixed(precision);
}

$(function(){
	String.prototype.toFixed=strToFixed;
	Number.prototype.toFixed = toFixed;
	Number.prototype.add = add;
	Number.prototype.subtract = subtract;
	Number.prototype.multiply = multiply;
	Number.prototype.divide = divide;
	Number.prototype.mod = mod;
});