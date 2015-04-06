PlaceHolder = function(id,value){
	this.id = id;
	this.value = value;
	this.bValue = false;
}

PlaceHolder.prototype={
	init: function(){
		if(typeof this.id == "string") {
			if(this.id.substr(0,1) !="#") { 
				this.id = "#"+this.id;
			} 
		}else { 
			this.id = "#"+ $(this.id).attr("id");
		}
		var ef = this;
		$(this.id).unbind("focus").unbind("focusout").focus(
				function(){
					if($(ef.id).val() == ef.value)
						$(ef.id).val("");
				}
			).focusout(
				function(){
					if($(ef.id).val() == ""){
						$(ef.id).val(ef.value);
						ef.bValue = false;
					}else{
						ef.bValue = true;
					}
				}
			);
	},
	refresh: function(){
		if($(this.id).val() == ""){
			$(this.id).val(this.value);
		}
	},
	getValue: function(){
		if($(this.id).val() == this.value)
			return "";
		else
			return $(this.id).val();
	},
	focusClear: function(){
	},
	ocusoutCheck: function(){
	}
}