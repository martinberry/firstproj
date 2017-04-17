function ZtrDropDown(trigger, dataParam){
	this.$trigger = trigger;
	this.childObj;
	this.getDate = dataParam.getData;
	this.textName = dataParam.textName;
	this.valueName = dataParam.valueName;
}

ZtrDropDown.prototype.casCading = function (childObj){
	this.childObj = childObj;
}

ZtrDropDown.prototype.init = function(parentValue){
	$(this.$trigger).find('ul.dropdown-menu').html('');
	var data = this.getDate(parentValue);
	if(data === undefined || data.length == 0){
		$(this.$trigger).find('ul.dropdown-menu').siblings(".dropdownBtn").find(".activeFonts").html(function(){
			return $(this).parent().attr('title');
		});
		return;
	}
	var tName = 'areaName';
	var vName = 'no';
	if(this.textName !== undefined)tName = this.textName;
	if(this.valueName !== undefined)vName = this.valueName;
	for(var i=0; i<data.length; i++){
		var a = '<a href="javascript:void(0);"></a>';
		var $a = $(a);
		if(typeof(data[i]) === 'string'){
			$a.html(data[i]);
			$a.attr('value',data[i]);
		}else{
			$a.html(data[i][tName]);
			$a.attr('value',data[i][vName]);
		}
		$(this.$trigger).find('ul.dropdown-menu').append('<li name="'+data[i].areaName+'">'+ $a.prop('outerHTML') +'</li>');
	}
	var child = this.childObj;
	$(this.$trigger).find('ul.dropdown-menu li a').click(function(){
		var selectedValue = $(this).attr('value');
        var selectedText = $(this).html();
        var $thisParents = $(this).parents(".dropdown-menu li");
        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(selectedText);
        $thisParents.addClass("active");
        $thisParents.siblings().removeClass("active");
        if(child !== undefined ){
        	if(selectedValue === undefined){
        		child.init(selectedText);
        	}else{
        		child.init(selectedValue);
        	}
        	child.unSelect();
        	if(child.childObj !== undefined){
        		child.childObj.remove();
        	}
        }
	});
//	$(this.$trigger).find('ul.dropdown-menu li:first a').click();
}

ZtrDropDown.prototype.select = function(selectedValue){
	$(this.$trigger).find('.dropdown-menu li.active').removeClass('active');
	if(selectedValue === undefined || selectedValue == '')this.unSelect();
	$(this.$trigger).find('.dropdown-menu li a').each(function(){
		if($(this).attr('value') == selectedValue || $(this).html() == selectedValue){
			$(this).click();
		}
	});
}

ZtrDropDown.prototype.unSelect = function(){
	$(this.$trigger).find('.dropdown-menu li.active').removeClass('active');
	$(this.$trigger).find('a.dropdownBtn span.activeFonts').html(function(){return $(this).parent().attr('title');});
	if(this.childObj !== undefined){
		this.childObj.unSelect();
	}
}
ZtrDropDown.prototype.remove = function(){
	$(this.$trigger).find('.dropdown-menu li').remove();
	$(this.$trigger).find('a.dropdownBtn span.activeFonts').html(function(){return $(this).parent().attr('title');});
	if(this.childObj !== undefined){
		this.childObj.remove();
	}
}

/**
 * 仅限国内
 * @param topNo
 * @returns {Array}
 */
function getAddress(topNo){
	var data = [];
	$.ajax({
	    type: 'POST',
	    url: basepath + '/autoAddress/load' ,
	    data: 'topNo='+topNo ,
	    dataType: 'json' ,
	    async: false,
	    success: function(result){
	    	data = result;
	    },
	});
	return data;
}