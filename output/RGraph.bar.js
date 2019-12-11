
if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};RGraph.Registry={};RGraph.Registry.store=[];RGraph.Registry.store['chart.event.handlers']=[];RGraph.Registry.store['__rgraph_event_listeners__']=[];RGraph.background={};RGraph.objects=[];RGraph.Resizing={};RGraph.events=[];RGraph.cursor=[];RGraph.ObjectRegistry={};RGraph.ObjectRegistry.objects={};RGraph.ObjectRegistry.objects.byUID=[];RGraph.ObjectRegistry.objects.byCanvasID=[];PI=Math.PI;HALFPI=PI/2;TWOPI=PI*2;ISFF=navigator.userAgent.indexOf('Firefox')!=-1;ISOPERA=navigator.userAgent.indexOf('Opera')!=-1;ISCHROME=navigator.userAgent.indexOf('Chrome')!=-1;ISSAFARI=navigator.userAgent.indexOf('Safari')!=-1;ISWEBKIT=navigator.userAgent.indexOf('WebKit')!=-1;RGraph.getScale=function(max,obj)
{if(max==0){return['0.2','0.4','0.6','0.8','1.0'];}
var original_max=max;if(max<=1){if(max>0.5){return[0.2,0.4,0.6,0.8,Number(1).toFixed(1)];}else if(max>=0.1){return obj.Get('chart.scale.round')?[0.2,0.4,0.6,0.8,1]:[0.1,0.2,0.3,0.4,0.5];}else{var tmp=max;var exp=0;while(tmp<1.01){exp+=1;tmp*=10;}
var ret=['2e-'+exp,'4e-'+exp,'6e-'+exp,'8e-'+exp,'10e-'+exp];if(max<=('5e-'+exp)){ret=['1e-'+exp,'2e-'+exp,'3e-'+exp,'4e-'+exp,'5e-'+exp];}
return ret;}}
if(String(max).indexOf('.')>0){max=String(max).replace(/\.\d+$/,'');}
var interval=Math.pow(10,Number(String(Number(max)).length-1));var topValue=interval;while(topValue<max){topValue+=(interval/2);}
if(Number(original_max)>Number(topValue)){topValue+=(interval/2);}
if(max<10){topValue=(Number(original_max)<=5?5:10);}
if(obj&&typeof(obj.Get('chart.scale.round'))=='boolean'&&obj.Get('chart.scale.round')){topValue=10*interval;}
return[topValue*0.2,topValue*0.4,topValue*0.6,topValue*0.8,topValue];}
RGraph.getScale2=function(obj,opt)
{var ca=obj.canvas;var co=obj.context;var prop=obj.properties;var numlabels=typeof(opt['ylabels.count'])=='number'?opt['ylabels.count']:5;var units_pre=typeof(opt['units.pre'])=='string'?opt['units.pre']:'';var units_post=typeof(opt['units.post'])=='string'?opt['units.post']:'';var max=Number(opt['max']);var min=typeof(opt['min'])=='number'?opt['min']:0;var strict=opt['strict'];var decimals=Number(opt['scale.decimals']);var point=opt['scale.point'];var thousand=opt['scale.thousand'];var original_max=max;var round=opt['scale.round'];var scale={'max':1,'labels':[]};if(!max){var max=1;var scale={max:1,min:0,labels:[]};for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1)).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else if(max<=1&&!strict){if(max>0.5){max=1;min=min;scale.min=min;for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)*(i+1))+min).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else if(max>=0.1){max=0.5;min=min;scale={'max':0.5,'min':min,'labels':[]}
for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1)).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else{scale={'min':min,'labels':[]}
var max_str=String(max);if(max_str.indexOf('e')>0){var numdecimals=Math.abs(max_str.substring(max_str.indexOf('e')+1));}else{var numdecimals=String(max).length-2;}
var max=1/Math.pow(10,numdecimals-1);for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1));label=label.toExponential();label=label.split(/e/);label[0]=Math.round(label[0]);label=label.join('e');scale.labels.push(label);}
tmp=scale.labels[scale.labels.length-1].split(/e/);tmp[0]+=0;tmp[1]=Number(tmp[1])-1;tmp=tmp[0]+'e'+tmp[1];scale.labels[scale.labels.length-1]=tmp;for(var i=0;i<scale.labels.length;++i){scale.labels[i]=units_pre+scale.labels[i]+units_post;}
scale.max=Number(max);}}else if(!strict){max=Math.ceil(max);var interval=Math.pow(10,Number(String(Number(max)-Number(min)).length-1));var topValue=interval;while(topValue<max){topValue+=(interval/2);}
if(Number(original_max)>Number(topValue)){topValue+=(interval/2);}
if(max<=10){topValue=(Number(original_max)<=5?5:10);}
if(obj&&typeof(round)=='boolean'&&round){topValue=10*interval;}
scale.max=topValue;var tmp_point=prop['chart.scale.point'];var tmp_thousand=prop['chart.scale.thousand'];obj.Set('chart.scale.thousand',thousand);obj.Set('chart.scale.point',point);for(var i=0;i<numlabels;++i){scale.labels.push(RGraph.number_format(obj,((((i+1)/numlabels)*(topValue-min))+min).toFixed(decimals),units_pre,units_post));}
obj.Set('chart.scale.thousand',tmp_thousand);obj.Set('chart.scale.point',tmp_point);}else if(typeof(max)=='number'&&strict){for(var i=0;i<numlabels;++i){scale.labels.push(RGraph.number_format(obj,((((i+1)/numlabels)*(max-min))+min).toFixed(decimals),units_pre,units_post));}
scale.max=max;}
scale.units_pre=units_pre;scale.units_post=units_post;scale.point=point;scale.decimals=decimals;scale.thousand=thousand;scale.numlabels=numlabels;scale.round=Boolean(round);scale.min=min;return scale;}
RGraph.array_max=function(arr)
{var max=null;if(typeof(arr)=='number'){return arr;}
for(var i=0;i<arr.length;++i){if(typeof(arr[i])=='number'){var val=arguments[1]?Math.abs(arr[i]):arr[i];if(typeof(max)=='number'){max=Math.max(max,val);}else{max=val;}}}
return max;}
RGraph.array_pad=function(arr,len)
{if(arr.length<len){var val=arguments[2]?arguments[2]:null;for(var i=arr.length;i<len;++i){arr[i]=val;}}
return arr;}
RGraph.array_sum=function(arr)
{if(typeof(arr)=='number'){return arr;}
var i,sum;var len=arr.length;for(i=0,sum=0;i<len;sum+=arr[i++]);return sum;}
RGraph.array_linearize=function()
{var arr=[];var args=arguments;for(var i=0;i<args.length;++i){if(typeof(args[i])=='object'&&args[i]){for(var j=0;j<arguments[i].length;++j){var sub=RGraph.array_linearize(args[i][j]);for(var k=0;k<sub.length;++k){arr.push(sub[k]);}}}else{arr.push(args[i]);}}
return arr;}
RGraph.Text=function(context,font,size,x,y,text)
{var args=arguments;if((typeof(text)!='string'&&typeof(text)!='number')||text=='undefined'){return;}
if(typeof(text)=='string'&&text.match(/\r\n/)){var dimensions=RGraph.MeasureText('M',args[11],font,size);var arr=text.split('\r\n');if(args[6]&&args[6]=='center')y=(y-(dimensions[1]*((arr.length-1)/2)));for(var i=1;i<arr.length;++i){RGraph.Text(context,font,size,args[9]==-90?(x+(size*1.5)):x,y+(dimensions[1]*i),arr[i],args[6]?args[6]:null,args[7],args[8],args[9],args[10],args[11],args[12]);}
text=arr[0];}
if(document.all&&ISOLD){y+=2;}
context.font=(args[11]?'Bold ':'')+size+'pt '+font;var i;var origX=x;var origY=y;var originalFillStyle=context.fillStyle;var originalLineWidth=context.lineWidth;if(typeof(args[6])=='undefined')args[6]='bottom';if(typeof(args[7])=='undefined')args[7]='left';if(typeof(args[8])=='undefined')args[8]=null;if(typeof(args[9])=='undefined')args[9]=0;if(navigator.userAgent.indexOf('Opera')!=-1){context.canvas.__rgraph_valign__=args[6];context.canvas.__rgraph_halign__=args[7];}
context.save();context.canvas.__rgraph_originalx__=x;context.canvas.__rgraph_originaly__=y;context.translate(x,y);x=0;y=0;if(args[9]){context.rotate(args[9]/(180/PI));}
if(args[6]){var vAlign=args[6];if(vAlign=='center'){context.textBaseline='middle';}else if(vAlign=='top'){context.textBaseline='top';}}
if(args[7]){var hAlign=args[7];var width=context.measureText(text).width;if(hAlign){if(hAlign=='center'){context.textAlign='center';}else if(hAlign=='right'){context.textAlign='right';}}}
context.fillStyle=originalFillStyle;context.save();context.fillText(text,0,0);context.lineWidth=1;var width=context.measureText(text).width;var width_offset=(hAlign=='center'?(width/2):(hAlign=='right'?width:0));var height=size*1.5;var height_offset=(vAlign=='center'?(height/2):(vAlign=='top'?height:0));var ieOffset=ISOLD?2:0;if(args[8]){context.strokeRect(-3-width_offset,0-3-height-ieOffset+height_offset,width+6,height+6);if(args[10]){context.fillStyle=args[10];context.fillRect(-3-width_offset,0-3-height-ieOffset+height_offset,width+6,height+6);}
context.fillStyle=originalFillStyle;context.fillText(text,0,0);}
context.restore();context.lineWidth=originalLineWidth;context.restore();}
RGraph.Clear=function(canvas)
{if(!canvas){return;}
RGraph.FireCustomEvent(canvas.__object__,'onbeforeclear');var context=canvas.getContext('2d');var color=arguments[1];if(ISIE8&&!color){color='white';}
if(!color||(color&&color=='rgba(0,0,0,0)'||color=='transparent')){context.clearRect(0,0,canvas.width,canvas.height);context.globalCompositeOperation='source-over';}else{context.fillStyle=color;context=canvas.getContext('2d');context.beginPath();if(ISIE8){context.fillRect(0,0,canvas.width,canvas.height);}else{context.fillRect(-10,-10,canvas.width+20,canvas.height+20);}
context.fill();}
if(RGraph.Registry.Get('chart.background.image.'+canvas.id)){var img=RGraph.Registry.Get('chart.background.image.'+canvas.id);img.style.position='absolute';img.style.left='-10000px';img.style.top='-10000px';}
if(RGraph.Registry.Get('chart.tooltip')){RGraph.HideTooltip(canvas);}
canvas.style.cursor='default';RGraph.FireCustomEvent(canvas.__object__,'onclear');}
RGraph.DrawTitle=function(obj,text,gutterTop)
{var ca=canvas=obj.canvas;var co=context=obj.context;var prop=obj.properties;var gutterLeft=prop['chart.gutter.left'];var gutterRight=prop['chart.gutter.right'];var gutterTop=gutterTop;var gutterBottom=prop['chart.gutter.bottom'];var size=arguments[4]?arguments[4]:12;var bold=prop['chart.title.bold'];var centerx=(arguments[3]?arguments[3]:((ca.width-gutterLeft-gutterRight)/2)+gutterLeft);var keypos=prop['chart.key.position'];var vpos=prop['chart.title.vpos'];var hpos=prop['chart.title.hpos'];var bgcolor=prop['chart.title.background'];var x=prop['chart.title.x'];var y=prop['chart.title.y'];var halign='center';var valign='center';if(obj.type=='bar'&&prop['chart.variant']=='3d'){keypos='gutter';}
co.beginPath();co.fillStyle=prop['chart.text.color']?prop['chart.text.color']:'black';if(keypos&&keypos!='gutter'){var valign='center';}else if(!keypos){var valign='center';}else{var valign='bottom';}
if(typeof(prop['chart.title.vpos'])=='number'){vpos=prop['chart.title.vpos']*gutterTop;if(prop['chart.xaxispos']=='top'){vpos=prop['chart.title.vpos']*gutterBottom+gutterTop+(ca.height-gutterTop-gutterBottom);}}else{vpos=gutterTop-size-5;if(prop['chart.xaxispos']=='top'){vpos=ca.height-gutterBottom+size+5;}}
if(typeof(hpos)=='number'){centerx=hpos*ca.width;}
if(typeof(x)=='number')centerx=x;if(typeof(y)=='number')vpos=y;if(typeof(prop['chart.title.halign'])=='string'){halign=prop['chart.title.halign'];}
if(typeof(prop['chart.title.valign'])=='string'){valign=prop['chart.title.valign'];}
if(typeof(prop['chart.title.color']!=null)){var oldColor=context.fillStyle
var newColor=prop['chart.title.color']
context.fillStyle=newColor?newColor:'black';}
var font=prop['chart.text.font'];if(typeof(prop['chart.title.font'])=='string'){font=prop['chart.title.font'];}
RGraph.Text2(obj,{'font':font,'size':size,'x':centerx,'y':vpos,'text':text,'valign':valign,'halign':halign,'bounding':bgcolor!=null,'bounding.fill':bgcolor,'bold':bold,'tag':'title'});context.fillStyle=oldColor;}
RGraph.getMouseXY=function(e)
{var el=e.target;var ca=el;var caStyle=ca.style;var offsetX=0;var offsetY=0;var x;var y;var ISFIXED=(ca.style.position=='fixed');var borderLeft=parseInt(caStyle.borderLeftWidth)||0;var borderTop=parseInt(caStyle.borderTopWidth)||0;var paddingLeft=parseInt(caStyle.paddingLeft)||0
var paddingTop=parseInt(caStyle.paddingTop)||0
var additionalX=borderLeft+paddingLeft;var additionalY=borderTop+paddingTop;if(typeof(e.offsetX)=='number'&&typeof(e.offsetY)=='number'){if(ISFIXED){if(ISOPERA){x=e.offsetX;y=e.offsetY;}else if(ISWEBKIT){x=e.offsetX-paddingLeft-borderLeft;y=e.offsetY-paddingTop-borderTop;}else if(ISIE){x=e.offsetX-paddingLeft;y=e.offsetY-paddingTop;}else{x=e.offsetX;y=e.offsetY;}}else{if(!ISIE&&!ISOPERA){x=e.offsetX-borderLeft-paddingLeft;y=e.offsetY-borderTop-paddingTop;}else if(ISIE){x=e.offsetX-paddingLeft;y=e.offsetY-paddingTop;}else{x=e.offsetX;y=e.offsetY;}}}else if(e.layerX&&e.layerY){if(!ca.style.position){ca.style.position='relative';ca.style.top=0;ca.style.left=0;}
x=e.layerX;y=e.layerY;if(ISFIXED){x=e.layerX-borderLeft-paddingLeft;y=e.layerY-borderTop-paddingTop;}}else{if(typeof(el.offsetParent)!='undefined'){do{offsetX+=el.offsetLeft;offsetY+=el.offsetTop;}while((el=el.offsetParent));}
x=e.pageX-offsetX-additionalX;y=e.pageY-offsetY-additionalY;x-=(2*(parseInt(document.body.style.borderLeftWidth)||0));y-=(2*(parseInt(document.body.style.borderTopWidth)||0));x+=(parseInt(caStyle.borderLeftWidth)||0);y+=(parseInt(caStyle.borderTopWidth)||0);}
return[x,y];}
RGraph.getCanvasXY=function(canvas)
{var x=0;var y=0;var el=canvas;do{x+=el.offsetLeft;y+=el.offsetTop;if(el.tagName.toLowerCase()=='table'&&(ISCHROME||ISSAFARI)){x+=parseInt(el.border)||0;y+=parseInt(el.border)||0;}
el=el.offsetParent;}while(el&&el.tagName.toLowerCase()!='body');var paddingLeft=canvas.style.paddingLeft?parseInt(canvas.style.paddingLeft):0;var paddingTop=canvas.style.paddingTop?parseInt(canvas.style.paddingTop):0;var borderLeft=canvas.style.borderLeftWidth?parseInt(canvas.style.borderLeftWidth):0;var borderTop=canvas.style.borderTopWidth?parseInt(canvas.style.borderTopWidth):0;if(navigator.userAgent.indexOf('Firefox')>0){x+=parseInt(document.body.style.borderLeftWidth)||0;y+=parseInt(document.body.style.borderTopWidth)||0;}
return[x+paddingLeft+borderLeft,y+paddingTop+borderTop];}
RGraph.isFixed=function(canvas)
{var obj=canvas;var i=0;while(obj.tagName.toLowerCase()!='body'&&i<99){if(obj.style.position=='fixed'){return obj;}
obj=obj.offsetParent;}
return false;}
RGraph.Register=function(obj)
{if(!obj.Get('chart.noregister')){RGraph.ObjectRegistry.Add(obj);obj.Set('chart.noregister',true);}}
RGraph.Redraw=function()
{var objectRegistry=RGraph.ObjectRegistry.objects.byCanvasID;var tags=document.getElementsByTagName('canvas');for(var i=0;i<tags.length;++i){if(tags[i].__object__&&tags[i].__object__.isRGraph){if(!tags[i].noclear){RGraph.Clear(tags[i],arguments[0]?arguments[0]:null);}}}
for(var i=0;i<objectRegistry.length;++i){if(objectRegistry[i]){var id=objectRegistry[i][0];objectRegistry[i][1].Draw();}}}
RGraph.RedrawCanvas=function(canvas)
{var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);if(!arguments[1]||(typeof(arguments[1])=='boolean'&&!arguments[1]==false)){RGraph.Clear(canvas);}
for(var i=0;i<objects.length;++i){if(objects[i]){if(objects[i]&&objects[i].isRGraph){objects[i].Draw();}}}}
RGraph.background.Draw=function(obj)
{var ca=canvas=obj.canvas;var co=context=obj.context;var prop=obj.properties;var height=0;var gutterLeft=obj.gutterLeft;var gutterRight=obj.gutterRight;var gutterTop=obj.gutterTop;var gutterBottom=obj.gutterBottom;var variant=prop['chart.variant'];co.fillStyle=prop['chart.text.color'];if(variant=='3d'){co.save();co.translate(10,-5);}
if(typeof(prop['chart.title.xaxis'])=='string'&&prop['chart.title.xaxis'].length){var size=prop['chart.text.size']+2;var font=prop['chart.text.font'];var bold=prop['chart.title.xaxis.bold'];if(typeof(prop['chart.title.xaxis.size'])=='number'){size=prop['chart.title.xaxis.size'];}
if(typeof(prop['chart.title.xaxis.font'])=='string'){font=prop['chart.title.xaxis.font'];}
var hpos=((ca.width-gutterLeft-gutterRight)/2)+gutterLeft;var vpos=ca.height-gutterBottom+25;if(typeof(prop['chart.title.xaxis.pos'])=='number'){vpos=ca.height-(gutterBottom*prop['chart.title.xaxis.pos']);}
RGraph.Text2(obj,{'font':font,'size':size,'x':hpos,'y':vpos,'text':prop['chart.title.xaxis'],'halign':'center','valign':'center','bold':bold,'tag':'title xaxis'});}
if(typeof(prop['chart.title.yaxis'])=='string'&&prop['chart.title.yaxis'].length){var size=prop['chart.text.size']+2;var font=prop['chart.text.font'];var angle=270;var bold=prop['chart.title.yaxis.bold'];var color=prop['chart.title.yaxis.color'];if(typeof(prop['chart.title.yaxis.pos'])=='number'){var yaxis_title_pos=prop['chart.title.yaxis.pos']*gutterLeft;}else{var yaxis_title_pos=((gutterLeft-25)/gutterLeft)*gutterLeft;}
if(typeof(prop['chart.title.yaxis.size'])=='number'){size=prop['chart.title.yaxis.size'];}
if(typeof(prop['chart.title.yaxis.font'])=='string'){font=prop['chart.title.yaxis.font'];}
if(prop['chart.title.yaxis.align']=='right'||prop['chart.title.yaxis.position']=='right'){angle=90;yaxis_title_pos=prop['chart.title.yaxis.pos']?(ca.width-gutterRight)+(prop['chart.title.yaxis.pos']*gutterRight):ca.width-gutterRight+prop['chart.text.size']+5;}else{yaxis_title_pos=yaxis_title_pos;}
context.fillStyle=color;RGraph.Text2(obj,{'font':font,'size':size,'x':yaxis_title_pos,'y':((ca.height-gutterTop-gutterBottom)/2)+gutterTop,'valign':'center','halign':'center','angle':angle,'bold':bold,'text':prop['chart.title.yaxis'],'tag':'title yaxis'});}
var bgcolor=prop['chart.background.color'];if(bgcolor){co.fillStyle=bgcolor;co.fillRect(gutterLeft,gutterTop,ca.width-gutterLeft-gutterRight,ca.height-gutterTop-gutterBottom);}
co.beginPath();co.fillStyle=prop['chart.background.barcolor1'];co.strokeStyle=co.fillStyle;height=(ca.height-gutterBottom);for(var i=gutterTop;i<height;i+=80){co.fillRect(gutterLeft,i,ca.width-gutterLeft-gutterRight,Math.min(40,ca.height-gutterBottom-i));}
co.fillStyle=prop['chart.background.barcolor2'];co.strokeStyle=co.fillStyle;height=(ca.height-gutterBottom);for(var i=(40+gutterTop);i<height;i+=80){co.fillRect(gutterLeft,i,ca.width-gutterLeft-gutterRight,i+40>(ca.height-gutterBottom)?ca.height-(gutterBottom+i):40);}
co.beginPath();if(prop['chart.background.grid']){if(prop['chart.background.grid.autofit']){if(prop['chart.background.grid.autofit.align']){obj.Set('chart.background.grid.autofit.numhlines',prop['chart.ylabels.count']);if(obj.type=='line'){if(prop['chart.labels']&&prop['chart.labels'].length){obj.Set('chart.background.grid.autofit.numvlines',prop['chart.labels'].length-1);}else{obj.Set('chart.background.grid.autofit.numvlines',obj.data[0].length-1);}}else if(obj.type=='bar'&&prop['chart.labels']&&prop['chart.labels'].length){obj.Set('chart.background.grid.autofit.numvlines',prop['chart.labels'].length);}}
var vsize=((ca.width-gutterLeft-gutterRight))/prop['chart.background.grid.autofit.numvlines'];var hsize=(ca.height-gutterTop-gutterBottom)/prop['chart.background.grid.autofit.numhlines'];obj.Set('chart.background.grid.vsize',vsize);obj.Set('chart.background.grid.hsize',hsize);}
co.beginPath();co.lineWidth=prop['chart.background.grid.width']?prop['chart.background.grid.width']:1;co.strokeStyle=prop['chart.background.grid.color'];if(prop['chart.background.grid.hlines']){height=(ca.height-gutterBottom)
for(y=gutterTop;y<height;y+=prop['chart.background.grid.hsize']){context.moveTo(gutterLeft,Math.round(y));context.lineTo(ca.width-gutterRight,Math.round(y));}}
if(prop['chart.background.grid.vlines']){var width=(ca.width-gutterRight)
for(x=gutterLeft;x<=width;x+=prop['chart.background.grid.vsize']){co.moveTo(Math.round(x),gutterTop);co.lineTo(Math.round(x),ca.height-gutterBottom);}}
if(prop['chart.background.grid.border']){co.strokeStyle=prop['chart.background.grid.color'];co.strokeRect(Math.round(gutterLeft),Math.round(gutterTop),ca.width-gutterLeft-gutterRight,ca.height-gutterTop-gutterBottom);}}
context.stroke();if(variant=='3d'){co.restore();}
if(typeof(prop['chart.title'])=='string'){if(obj.type=='gantt'){gutterTop-=10;}
RGraph.DrawTitle(obj,prop['chart.title'],gutterTop,null,prop['chart.title.size']?prop['chart.title.size']:prop['chart.text.size']+2);}
co.stroke();}
RGraph.array_clone=function(obj)
{if(obj==null||typeof(obj)!='object'){return obj;}
var temp=[];for(var i=0;i<obj.length;++i){if(typeof(obj[i])=='number'){temp[i]=(function(arg){return Number(arg);})(obj[i]);}else if(typeof(obj[i])=='string'){temp[i]=(function(arg){return String(arg);})(obj[i]);}else if(typeof(obj[i])=='function'){temp[i]=obj[i];}else{temp[i]=RGraph.array_clone(obj[i]);}}
return temp;}
RGraph.number_format=function(obj,num)
{var ca=obj.canvas;var co=obj.context;var prop=obj.properties;var i;var prepend=arguments[2]?String(arguments[2]):'';var append=arguments[3]?String(arguments[3]):'';var output='';var decimal='';var decimal_seperator=typeof(prop['chart.scale.point'])=='string'?prop['chart.scale.point']:'.';var thousand_seperator=typeof(prop['chart.scale.thousand'])=='string'?prop['chart.scale.thousand']:',';RegExp.$1='';var i,j;if(typeof(prop['chart.scale.formatter'])=='function'){return prop['chart.scale.formatter'](obj,num);}
if(String(num).indexOf('e')>0){return String(prepend+String(num)+append);}
num=String(num);if(num.indexOf('.')>0){var tmp=num;num=num.replace(/\.(.*)/,'');decimal=tmp.replace(/(.*)\.(.*)/,'$2');}
var seperator=thousand_seperator;var foundPoint;for(i=(num.length-1),j=0;i>=0;j++,i--){var character=num.charAt(i);if(j%3==0&&j!=0){output+=seperator;}
output+=character;}
var rev=output;output='';for(i=(rev.length-1);i>=0;i--){output+=rev.charAt(i);}
if(output.indexOf('-'+prop['chart.scale.thousand'])==0){output='-'+output.substr(('-'+prop['chart.scale.thousand']).length);}
if(decimal.length){output=output+decimal_seperator+decimal;decimal='';RegExp.$1='';}
if(output.charAt(0)=='-'){output=output.replace(/-/,'');prepend='-'+prepend;}
return prepend+output+append;}
RGraph.DrawBars=function(obj)
{var hbars=obj.Get('chart.background.hbars');obj.context.beginPath();for(i=0;i<hbars.length;++i){var start=hbars[i][0];var length=hbars[i][1];var color=hbars[i][2];if(RGraph.is_null(start))start=obj.scale2.max
if(start>obj.scale2.max)start=obj.scale2.max;if(RGraph.is_null(length))length=obj.scale2.max-start;if(start+length>obj.scale2.max)length=obj.scale2.max-start;if(start+length<(-1*obj.scale2.max))length=(-1*obj.scale2.max)-start;if(obj.properties['chart.xaxispos']=='center'&&start==obj.scale2.max&&length<(obj.scale2.max*-2)){length=obj.scale2.max*-2;}
var x=obj.Get('chart.gutter.left');var y=obj.getYCoord(start);var w=obj.canvas.width-obj.Get('chart.gutter.left')-obj.Get('chart.gutter.right');var h=obj.getYCoord(start+length)-y;if(ISOPERA!=-1&&obj.Get('chart.xaxispos')=='center'&&h<0){h*=-1;y=y-h;}
if(obj.Get('chart.xaxispos')=='top'){y=obj.canvas.height-y;h*=-1;}
obj.context.fillStyle=color;obj.context.fillRect(x,y,w,h);}}
RGraph.DrawInGraphLabels=function(obj)
{var canvas=obj.canvas;var context=obj.context;var labels=obj.Get('chart.labels.ingraph');var labels_processed=[];var fgcolor='black';var bgcolor='white';var direction=1;if(!labels){return;}
for(var i=0;i<labels.length;++i){if(typeof(labels[i])=='number'){for(var j=0;j<labels[i];++j){labels_processed.push(null);}}else if(typeof(labels[i])=='string'||typeof(labels[i])=='object'){labels_processed.push(labels[i]);}else{labels_processed.push('');}}
RGraph.NoShadow(obj);if(labels_processed&&labels_processed.length>0){for(var i=0;i<labels_processed.length;++i){if(labels_processed[i]){var coords=obj.coords[i];if(coords&&coords.length>0){var x=(obj.type=='bar'?coords[0]+(coords[2]/2):coords[0]);var y=(obj.type=='bar'?coords[1]+(coords[3]/2):coords[1]);var length=typeof(labels_processed[i][4])=='number'?labels_processed[i][4]:25;context.beginPath();context.fillStyle='black';context.strokeStyle='black';if(obj.type=='bar'){if(obj.Get('chart.xaxispos')=='top'){length*=-1;}
if(obj.Get('chart.variant')=='dot'){context.moveTo(Math.round(x),obj.coords[i][1]-5);context.lineTo(Math.round(x),obj.coords[i][1]-5-length);var text_x=Math.round(x);var text_y=obj.coords[i][1]-5-length;}else if(obj.Get('chart.variant')=='arrow'){context.moveTo(Math.round(x),obj.coords[i][1]-5);context.lineTo(Math.round(x),obj.coords[i][1]-5-length);var text_x=Math.round(x);var text_y=obj.coords[i][1]-5-length;}else{context.arc(Math.round(x),y,2.5,0,6.28,0);context.moveTo(Math.round(x),y);context.lineTo(Math.round(x),y-length);var text_x=Math.round(x);var text_y=y-length;}
context.stroke();context.fill();}else if(obj.type=='line'){if(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][3])=='number'&&labels_processed[i][3]==-1){context.moveTo(Math.round(x),y+5);context.lineTo(Math.round(x),y+5+length);context.stroke();context.beginPath();context.moveTo(Math.round(x),y+5);context.lineTo(Math.round(x)-3,y+10);context.lineTo(Math.round(x)+3,y+10);context.closePath();var text_x=x;var text_y=y+5+length;}else{var text_x=x;var text_y=y-5-length;context.moveTo(Math.round(x),y-5);context.lineTo(Math.round(x),y-5-length);context.stroke();context.beginPath();context.moveTo(Math.round(x),y-5);context.lineTo(Math.round(x)-3,y-10);context.lineTo(Math.round(x)+3,y-10);context.closePath();}
context.fill();}
context.beginPath();context.fillStyle=(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][1])=='string')?labels_processed[i][1]:'black';RGraph.Text2(obj,{'font':obj.Get('chart.text.font'),'size':obj.Get('chart.text.size'),'x':text_x,'y':text_y,'text':(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][0])=='string')?labels_processed[i][0]:labels_processed[i],'valign':'bottom','halign':'center','bounding':true,'bounding.fill':(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][2])=='string')?labels_processed[i][2]:'white','tag':'labels ingraph'});context.fill();}}}}}
RGraph.FixEventObject=function(e)
{if(ISOLD){var e=event;e.pageX=(event.clientX+document.body.scrollLeft);e.pageY=(event.clientY+document.body.scrollTop);e.target=event.srcElement;if(!document.body.scrollTop&&document.documentElement.scrollTop){e.pageX+=parseInt(document.documentElement.scrollLeft);e.pageY+=parseInt(document.documentElement.scrollTop);}}
if(typeof(e.offsetX)=='undefined'&&typeof(e.offsetY)=='undefined'){var coords=RGraph.getMouseXY(e);e.offsetX=coords[0];e.offsetY=coords[1];}
if(!e.stopPropagation){e.stopPropagation=function(){window.event.cancelBubble=true;}}
return e;}
RGraph.HideCrosshairCoords=function()
{var div=RGraph.Registry.Get('chart.coordinates.coords.div');if(div&&div.style.opacity==1&&div.__object__.Get('chart.crosshairs.coords.fadeout')){setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.9;},50);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.8;},100);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.7;},150);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.6;},200);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.5;},250);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.4;},300);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.3;},350);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.2;},400);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0.1;},450);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.opacity=0;},500);setTimeout(function(){RGraph.Registry.Get('chart.coordinates.coords.div').style.display='none';},550);}}
RGraph.Draw3DAxes=function(obj)
{var gutterLeft=obj.Get('chart.gutter.left');var gutterRight=obj.Get('chart.gutter.right');var gutterTop=obj.Get('chart.gutter.top');var gutterBottom=obj.Get('chart.gutter.bottom');var context=obj.context;var canvas=obj.canvas;context.strokeStyle='#aaa';context.fillStyle='#ddd';context.beginPath();context.moveTo(gutterLeft,gutterTop);context.lineTo(gutterLeft+10,gutterTop-5);context.lineTo(gutterLeft+10,canvas.height-gutterBottom-5);context.lineTo(gutterLeft,canvas.height-gutterBottom);context.closePath();context.stroke();context.fill();context.beginPath();context.moveTo(gutterLeft,canvas.height-gutterBottom);context.lineTo(gutterLeft+10,canvas.height-gutterBottom-5);context.lineTo(canvas.width-gutterRight+10,canvas.height-gutterBottom-5);context.lineTo(canvas.width-gutterRight,canvas.height-gutterBottom);context.closePath();context.stroke();context.fill();}
RGraph.OldBrowserCompat=function(context)
{if(!context){return;}
if(!context.measureText){context.measureText=function(text)
{var textObj=document.createElement('DIV');textObj.innerHTML=text;textObj.style.position='absolute';textObj.style.top='-100px';textObj.style.left=0;document.body.appendChild(textObj);var width={width:textObj.offsetWidth};textObj.style.display='none';return width;}}
if(!context.fillText){context.fillText=function(text,targetX,targetY)
{return false;}}
if(!context.canvas.addEventListener){window.addEventListener=function(ev,func,bubble)
{return this.attachEvent('on'+ev,func);}
context.canvas.addEventListener=function(ev,func,bubble)
{return this.attachEvent('on'+ev,func);}}}
RGraph.strokedCurvyRect=function(context,x,y,w,h)
{var r=arguments[5]?arguments[5]:3;var corner_tl=(arguments[6]||arguments[6]==null)?true:false;var corner_tr=(arguments[7]||arguments[7]==null)?true:false;var corner_br=(arguments[8]||arguments[8]==null)?true:false;var corner_bl=(arguments[9]||arguments[9]==null)?true:false;context.beginPath();context.moveTo(x+(corner_tl?r:0),y);context.lineTo(x+w-(corner_tr?r:0),y);if(corner_tr){context.arc(x+w-r,y+r,r,PI+HALFPI,TWOPI,false);}
context.lineTo(x+w,y+h-(corner_br?r:0));if(corner_br){context.arc(x+w-r,y-r+h,r,TWOPI,HALFPI,false);}
context.lineTo(x+(corner_bl?r:0),y+h);if(corner_bl){context.arc(x+r,y-r+h,r,HALFPI,PI,false);}
context.lineTo(x,y+(corner_tl?r:0));if(corner_tl){context.arc(x+r,y+r,r,PI,PI+HALFPI,false);}
context.stroke();}
RGraph.filledCurvyRect=function(context,x,y,w,h)
{var r=arguments[5]?arguments[5]:3;var corner_tl=(arguments[6]||arguments[6]==null)?true:false;var corner_tr=(arguments[7]||arguments[7]==null)?true:false;var corner_br=(arguments[8]||arguments[8]==null)?true:false;var corner_bl=(arguments[9]||arguments[9]==null)?true:false;context.beginPath();if(corner_tl){context.moveTo(x+r,y+r);context.arc(x+r,y+r,r,PI,PI+HALFPI,false);}else{context.fillRect(x,y,r,r);}
if(corner_tr){context.moveTo(x+w-r,y+r);context.arc(x+w-r,y+r,r,PI+HALFPI,0,false);}else{context.moveTo(x+w-r,y);context.fillRect(x+w-r,y,r,r);}
if(corner_br){context.moveTo(x+w-r,y+h-r);context.arc(x+w-r,y-r+h,r,0,HALFPI,false);}else{context.moveTo(x+w-r,y+h-r);context.fillRect(x+w-r,y+h-r,r,r);}
if(corner_bl){context.moveTo(x+r,y+h-r);context.arc(x+r,y-r+h,r,HALFPI,PI,false);}else{context.moveTo(x,y+h-r);context.fillRect(x,y+h-r,r,r);}
context.fillRect(x+r,y,w-r-r,h);context.fillRect(x,y+r,r+1,h-r-r);context.fillRect(x+w-r-1,y+r,r+1,h-r-r);context.fill();}
RGraph.HideZoomedCanvas=function()
{var interval=15;var frames=10;if(typeof(__zoomedimage__)=='object'){obj=__zoomedimage__.obj;}else{return;}
if(obj.Get('chart.zoom.fade.out')){for(var i=frames,j=1;i>=0;--i,++j){if(typeof(__zoomedimage__)=='object'){setTimeout("__zoomedimage__.style.opacity = "+String(i/10),j*interval);}}
if(typeof(__zoomedbackground__)=='object'){setTimeout("__zoomedbackground__.style.opacity = "+String(i/frames),j*interval);}}
if(typeof(__zoomedimage__)=='object'){setTimeout("__zoomedimage__.style.display = 'none'",obj.Get('chart.zoom.fade.out')?(frames*interval)+10:0);}
if(typeof(__zoomedbackground__)=='object'){setTimeout("__zoomedbackground__.style.display = 'none'",obj.Get('chart.zoom.fade.out')?(frames*interval)+10:0);}}
RGraph.AddCustomEventListener=function(obj,name,func)
{if(typeof(RGraph.events[obj.uid])=='undefined'){RGraph.events[obj.uid]=[];}
RGraph.events[obj.uid].push([obj,name,func]);return RGraph.events[obj.uid].length-1;}
RGraph.FireCustomEvent=function(obj,name)
{if(obj&&obj.isRGraph){if(obj[name]){(obj[name])(obj);}
var uid=obj.uid;if(typeof(uid)=='string'&&typeof(RGraph.events)=='object'&&typeof(RGraph.events[uid])=='object'&&RGraph.events[uid].length>0){for(var j=0;j<RGraph.events[uid].length;++j){if(RGraph.events[uid][j]&&RGraph.events[uid][j][1]==name){RGraph.events[uid][j][2](obj);}}}}}
RGraph.SetConfig=function(obj,c)
{for(i in c){if(typeof(i)=='string'){obj.Set(i,c[i]);}}
return obj;}
RGraph.RemoveAllCustomEventListeners=function()
{var id=arguments[0];if(id&&RGraph.events[id]){RGraph.events[id]=[];}else{RGraph.events=[];}}
RGraph.RemoveCustomEventListener=function(obj,i)
{if(typeof(RGraph.events)=='object'&&typeof(RGraph.events[obj.id])=='object'&&typeof(RGraph.events[obj.id][i])=='object'){RGraph.events[obj.id][i]=null;}}
RGraph.DrawBackgroundImage=function(obj)
{if(typeof(obj.Get('chart.background.image'))=='string'){if(typeof(obj.canvas.__rgraph_background_image__)=='undefined'){var img=new Image();img.__object__=obj;img.__canvas__=obj.canvas;img.__context__=obj.context;img.src=obj.Get('chart.background.image');obj.canvas.__rgraph_background_image__=img;}else{img=obj.canvas.__rgraph_background_image__;}
img.onload=function()
{obj.__rgraph_background_image_loaded__=true;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);}
var gutterLeft=obj.Get('chart.gutter.left');var gutterRight=obj.Get('chart.gutter.right');var gutterTop=obj.Get('chart.gutter.top');var gutterBottom=obj.Get('chart.gutter.bottom');var stretch=obj.Get('chart.background.image.stretch');var align=obj.Get('chart.background.image.align');if(typeof(align)=='string'){if(align.indexOf('right')!=-1){var x=obj.canvas.width-img.width-gutterRight;}else{var x=gutterLeft;}
if(align.indexOf('bottom')!=-1){var y=obj.canvas.height-img.height-gutterBottom;}else{var y=gutterTop;}}else{var x=gutterLeft;var y=gutterTop;}
var x=typeof(obj.Get('chart.background.image.x'))=='number'?obj.Get('chart.background.image.x'):x;var y=typeof(obj.Get('chart.background.image.y'))=='number'?obj.Get('chart.background.image.y'):y;var w=stretch?obj.canvas.width-gutterLeft-gutterRight:img.width;var h=stretch?obj.canvas.height-gutterTop-gutterBottom:img.height;if(typeof(obj.Get('chart.background.image.w'))=='number')w=obj.Get('chart.background.image.w');if(typeof(obj.Get('chart.background.image.h'))=='number')h=obj.Get('chart.background.image.h');obj.context.drawImage(img,x,y,w,h);}}
RGraph.hasTooltips=function(obj)
{if(typeof(obj.Get('chart.tooltips'))=='object'&&obj.Get('chart.tooltips')){for(var i=0;i<obj.Get('chart.tooltips').length;++i){if(!RGraph.is_null(obj.Get('chart.tooltips')[i])){return true;}}}else if(typeof(obj.Get('chart.tooltips'))=='function'){return true;}
return false;}
RGraph.CreateUID=function()
{return'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,function(c)
{var r=Math.random()*16|0,v=c=='x'?r:(r&0x3|0x8);return v.toString(16);});}
RGraph.ObjectRegistry.Add=function(obj)
{var uid=obj.uid;var canvasID=obj.canvas.id;RGraph.ObjectRegistry.objects.byUID.push([uid,obj]);RGraph.ObjectRegistry.objects.byCanvasID.push([canvasID,obj]);}
RGraph.ObjectRegistry.Remove=function(obj)
{var id=obj.id;var uid=obj.uid;for(var i=0;i<RGraph.ObjectRegistry.objects.byUID.length;++i){if(RGraph.ObjectRegistry.objects.byUID[i]&&RGraph.ObjectRegistry.objects.byUID[i][1].uid==uid){RGraph.ObjectRegistry.objects.byUID[i]=null;}}
for(var i=0;i<RGraph.ObjectRegistry.objects.byCanvasID.length;++i){if(RGraph.ObjectRegistry.objects.byCanvasID[i]&&RGraph.ObjectRegistry.objects.byCanvasID[i][1]&&RGraph.ObjectRegistry.objects.byCanvasID[i][1].uid==uid){RGraph.ObjectRegistry.objects.byCanvasID[i]=null;}}}
RGraph.ObjectRegistry.Clear=function()
{if(arguments[0]){var id=(typeof(arguments[0])=='object'?arguments[0].id:arguments[0]);var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(id);for(var i=0;i<objects.length;++i){RGraph.ObjectRegistry.Remove(objects[i]);}}else{RGraph.ObjectRegistry.objects={};RGraph.ObjectRegistry.objects.byUID=[];RGraph.ObjectRegistry.objects.byCanvasID=[];}}
RGraph.ObjectRegistry.List=function()
{var list=[];for(var i=0;i<RGraph.ObjectRegistry.objects.byUID.length;++i){if(RGraph.ObjectRegistry.objects.byUID[i]){list.push(RGraph.ObjectRegistry.objects.byUID[i][1].type);}}
if(arguments[0]){return list;}else{p(list);}}
RGraph.ObjectRegistry.ClearByType=function(type)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]){var uid=objects[i][0];var obj=objects[i][1];if(obj&&obj.type==type){RGraph.ObjectRegistry.Remove(obj);}}}}
RGraph.ObjectRegistry.Iterate=function(func)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(typeof arguments[1]=='string'){var types=arguments[1].split(/,/);for(var j=0;j<types.length;++j){if(types[j]==objects[i][1].type){func(objects[i][1]);}}}else{func(objects[i][1]);}}}
RGraph.ObjectRegistry.getObjectsByCanvasID=function(id)
{var store=RGraph.ObjectRegistry.objects.byCanvasID;var ret=[];for(var i=0;i<store.length;++i){if(store[i]&&store[i][0]==id){ret.push(store[i][1]);}}
return ret;}
RGraph.ObjectRegistry.getFirstObjectByXY=RGraph.ObjectRegistry.getObjectByXY=function(e)
{var canvas=e.target;var ret=null;var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);for(var i=(objects.length-1);i>=0;--i){var obj=objects[i].getObjectByXY(e);if(obj){return obj;}}}
RGraph.ObjectRegistry.getObjectsByXY=function(e)
{var canvas=e.target;var ret=[];var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);for(var i=(objects.length-1);i>=0;--i){var obj=objects[i].getObjectByXY(e);if(obj){ret.push(obj);}}
return ret;}
RGraph.ObjectRegistry.getObjectByUID=function(uid)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1].uid==uid){return objects[i][1];}}}
RGraph.ObjectRegistry.getObjectsByType=function(type)
{var objects=RGraph.ObjectRegistry.objects.byUID;var ret=[];for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1]&&objects[i][1].type&&objects[i][1].type&&objects[i][1].type==type){ret.push(objects[i][1]);}}
return ret;}
RGraph.ObjectRegistry.getFirstObjectByType=function(type)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1]&&objects[i][1].type==type){return objects[i][1];}}
return null;}
RGraph.getAngleByXY=function(cx,cy,x,y)
{var angle=Math.atan((y-cy)/(x-cx));angle=Math.abs(angle)
if(x>=cx&&y>=cy){angle+=TWOPI;}else if(x>=cx&&y<cy){angle=(HALFPI-angle)+(PI+HALFPI);}else if(x<cx&&y<cy){angle+=PI;}else{angle=PI-angle;}
if(angle>TWOPI){angle-=TWOPI;}
return angle;}
RGraph.getHypLength=function(x1,y1,x2,y2)
{var ret=Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));return ret;}
RGraph.getRadiusEndPoint=function(cx,cy,angle,radius)
{var x=cx+(Math.cos(angle)*radius);var y=cy+(Math.sin(angle)*radius);return[x,y];}
RGraph.InstallEventListeners=function(obj)
{if(RGraph.isOld()){return;}
if(RGraph.InstallCanvasClickListener){RGraph.InstallWindowMousedownListener(obj);RGraph.InstallWindowMouseupListener(obj);RGraph.InstallCanvasMousemoveListener(obj);RGraph.InstallCanvasMouseupListener(obj);RGraph.InstallCanvasMousedownListener(obj);RGraph.InstallCanvasClickListener(obj);}else if(RGraph.hasTooltips(obj)||obj.Get('chart.adjustable')||obj.Get('chart.annotatable')||obj.Get('chart.contextmenu')||obj.Get('chart.resizable')||obj.Get('chart.key.interactive')||obj.Get('chart.events.click')||obj.Get('chart.events.mousemove')||typeof obj.onclick=='function'||typeof obj.onmousemove=='function'){alert('[RGRAPH] You appear to have used dynamic features but not included the file: RGraph.common.dynamic.js');}}
RGraph.pr=function(obj)
{var indent=(arguments[2]?arguments[2]:'    ');var str='';var counter=typeof arguments[3]=='number'?arguments[3]:0;if(counter>=5){return'';}
switch(typeof obj){case'string':str+=obj+' ('+(typeof obj)+', '+obj.length+')';break;case'number':str+=obj+' ('+(typeof obj)+')';break;case'boolean':str+=obj+' ('+(typeof obj)+')';break;case'function':str+='function () {}';break;case'undefined':str+='undefined';break;case'null':str+='null';break;case'object':if(RGraph.is_null(obj)){str+=indent+'null\n';}else{str+=indent+'Object {'+'\n'
for(j in obj){str+=indent+'    '+j+' => '+RGraph.pr(obj[j],true,indent+'    ',counter+1)+'\n';}
str+=indent+'}';}
break;default:str+='Unknown type: '+typeof obj+'';break;}
if(!arguments[1]){alert(str);}
return str;}
RGraph.DashedLine=function(context,x1,y1,x2,y2)
{var size=5;if(typeof(arguments[5])=='number'){size=arguments[5];}
var dx=x2-x1;var dy=y2-y1;var num=Math.floor(Math.sqrt((dx*dx)+(dy*dy))/size);var xLen=dx/num;var yLen=dy/num;var count=0;do{(count%2==0&&count>0)?context.lineTo(x1,y1):context.moveTo(x1,y1);x1+=xLen;y1+=yLen;}while(count++<=num);}
RGraph.AJAX=function(url,callback)
{if(window.XMLHttpRequest){var httpRequest=new XMLHttpRequest();}else if(window.ActiveXObject){var httpRequest=new ActiveXObject("Microsoft.XMLHTTP");}
httpRequest.onreadystatechange=function()
{if(this.readyState==4&&this.status==200){this.__user_callback__=callback;this.__user_callback__();}}
httpRequest.open('GET',url,true);httpRequest.send();}
RGraph.RotateCanvas=function(canvas,x,y,angle)
{var context=canvas.getContext('2d');context.translate(x,y);context.rotate(angle);context.translate(0-x,0-y);}
RGraph.MeasureText=function(text,bold,font,size)
{if(typeof(__rgraph_measuretext_cache__)=='undefined'){__rgraph_measuretext_cache__=[];}
var str=text+':'+bold+':'+font+':'+size;if(typeof(__rgraph_measuretext_cache__)=='object'&&__rgraph_measuretext_cache__[str]){return __rgraph_measuretext_cache__[str];}
if(!__rgraph_measuretext_cache__['text-div']){var div=document.createElement('DIV');div.style.position='absolute';div.style.top='-100px';div.style.left='-100px';document.body.appendChild(div);__rgraph_measuretext_cache__['text-div']=div;}else if(__rgraph_measuretext_cache__['text-div']){var div=__rgraph_measuretext_cache__['text-div'];}
div.innerHTML=text.replace(/\r\n/g,'<br />');div.style.fontFamily=font;div.style.fontWeight=bold?'bold':'normal';div.style.fontSize=size+'pt';var size=[div.offsetWidth,div.offsetHeight];__rgraph_measuretext_cache__[str]=size;return size;}
RGraph.Text2=function(obj,opt)
{if(obj&&obj.isRGraph){var co=obj.context;var ca=obj.canvas;}else if(typeof obj=='string'){var ca=document.getElementById(obj);var co=ca.getContext('2d');}else if(typeof obj.getContext=='function'){var ca=obj;var co=ca.getContext('2d');}else if(obj.toString().indexOf('CanvasRenderingContext2D')!=-1){var co=obj;var ca=obj.context;}
var x=opt.x;var y=opt.y;var originalX=x;var originalY=y;var text=opt.text;var text_multiline=text.split(/\r?\n/g);var numlines=text_multiline.length;var font=opt.font?opt.font:'Arial';var size=opt.size?opt.size:10;var size_pixels=size*1.5;var bold=opt.bold;var halign=opt.halign?opt.halign:'left';var valign=opt.valign?opt.valign:'bottom';var tag=typeof opt.tag=='string'&&opt.tag.length>0?opt.tag:'';var marker=opt.marker;var angle=opt.angle||0;if(typeof opt.boundingFill=='string')opt['bounding.fill']=opt.boundingFill;if(typeof opt.boundingStroke=='string')opt['bounding.stroke']=opt.boundingStroke;var bounding=opt.bounding;var bounding_stroke=opt['bounding.stroke']?opt['bounding.stroke']:'black';var bounding_fill=opt['bounding.fill']?opt['bounding.fill']:'rgba(255,255,255,0.7)';var bounding_shadow=opt['bounding.shadow'];var bounding_shadow_color=opt['bounding.shadow.color']||'#ccc';var bounding_shadow_blur=opt['bounding.shadow.blur']||3;var bounding_shadow_offsetx=opt['bounding.shadow.offsetx']||3;var bounding_shadow_offsety=opt['bounding.shadow.offsety']||3;var bounding_linewidth=opt['bounding.linewidth']||1;var ret={};if(typeof text=='number'){text=String(text);}
if(typeof text!='string'){alert('[RGRAPH TEXT] The text given must a string or a number');return;}
if(angle!=0){co.save();co.translate(x,y);co.rotate((Math.PI/180)*angle)
x=0;y=0;}
co.font=(opt.bold?'bold ':'')+size+'pt '+font;var width=0;for(var i=0;i<numlines;++i){width=Math.max(width,co.measureText(text_multiline[i]).width);}
var height=size_pixels*numlines;if(document.all&&ISOLD){}
if(opt.marker){var marker_size=10;var strokestyle=co.strokeStyle;obj.context.beginPath();co.strokeStyle='red';co.moveTo(x,y-marker_size);co.lineTo(x,y+marker_size);co.moveTo(x-marker_size,y);co.lineTo(x+marker_size,y);co.stroke();co.strokeStyle=strokestyle;}
if(halign=='center'){co.textAlign='center';var boundingX=x-2-(width/2);}else if(halign=='right'){co.textAlign='right';var boundingX=x-2-width;}else{co.textAlign='left';var boundingX=x-2;}
if(valign=='center'){co.textBaseline='middle';y-=1;y-=((numlines-1)/2)*size_pixels;var boundingY=y-(size_pixels/2)-2;}else if(valign=='top'){co.textBaseline='top';var boundingY=y-2;}else{co.textBaseline='bottom';if(numlines>1){y-=((numlines-1)*size_pixels);}
var boundingY=y-size_pixels-2;}
var boundingW=width+4;var boundingH=height+4;if(bounding){var pre_bounding_linewidth=co.lineWidth;var pre_bounding_strokestyle=co.strokeStyle;var pre_bounding_fillstyle=co.fillStyle;var pre_bounding_shadowcolor=co.shadowColor;var pre_bounding_shadowblur=co.shadowBlur;var pre_bounding_shadowoffsetx=co.shadowOffsetX;var pre_bounding_shadowoffsety=co.shadowOffsetY;co.lineWidth=bounding_linewidth;co.strokeStyle=bounding_stroke;co.fillStyle=bounding_fill;if(bounding_shadow){co.shadowColor=bounding_shadow_color;co.shadowBlur=bounding_shadow_blur;co.shadowOffsetX=bounding_shadow_offsetx;co.shadowOffsetY=bounding_shadow_offsety;}
co.strokeRect(boundingX,boundingY,boundingW,boundingH);co.fillRect(boundingX,boundingY,boundingW,boundingH);co.lineWidth=pre_bounding_linewidth;co.strokeStyle=pre_bounding_strokestyle;co.fillStyle=pre_bounding_fillstyle;co.shadowColor=pre_bounding_shadowcolor
co.shadowBlur=pre_bounding_shadowblur
co.shadowOffsetX=pre_bounding_shadowoffsetx
co.shadowOffsetY=pre_bounding_shadowoffsety}
if(numlines>1){for(var i=0;i<numlines;++i){co.fillText(text_multiline[i],x,y+(size_pixels*i));}}else{co.fillText(text,x,y);}
if(angle!=0){if(angle==90){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}}}else if(angle==180){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-width-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-width-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-width-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-(width/2)-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-(width/2)-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-(width/2)-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}}else if(angle==270){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-4;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-4;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}}}
obj.context.restore();}
co.textBaseline='alphabetic';co.textAlign='left';ret.x=boundingX;ret.y=boundingY;ret.width=boundingW;ret.height=boundingH
ret.object=obj;ret.text=text;ret.tag=tag;if(obj&&obj.isRGraph&&obj.coordsText){obj.coordsText.push(ret);}
return ret;}
RGraph.sequentialIndexToGrouped=function(index,data)
{var group=0;var grouped_index=0;while(--index>=0){if(typeof data[group]=='number'){group++
grouped_index=0;continue;}
grouped_index++;if(grouped_index>=data[group].length){group++;grouped_index=0;}}
return[group,grouped_index];}
RGraph.LinearGradient=function(obj,x1,y1,x2,y2,color1,color2){var gradient=obj.context.createLinearGradient(x1,y1,x2,y2);var numColors=arguments.length-5;for(var i=5;i<arguments.length;++i){var color=arguments[i];var stop=(i-5)/(numColors-1);gradient.addColorStop(stop,color);}return gradient;}
RGraph.RadialGradient=function(obj,x1,y1,r1,x2,y2,r2,color1,color2){var gradient=obj.context.createRadialGradient(x1,y1,r1,x2,y2,r2);var numColors=arguments.length-7;for(var i=7;i<arguments.length;++i){var color=arguments[i];var stop=(i-7)/(numColors-1);gradient.addColorStop(stop,color);}return gradient;}
RGraph.array_shift=function(arr){var ret=[];for(var i=1;i<arr.length;++i){ret.push(arr[i]);}return ret;}
RGraph.AddEventListener=function(id,e,func){var type=arguments[3]?arguments[3]:'unknown';RGraph.Registry.Get('chart.event.handlers').push([id,e,func,type]);}
RGraph.ClearEventListeners=function(id){if(id&&id=='window'){window.removeEventListener('mousedown',window.__rgraph_mousedown_event_listener_installed__,false);window.removeEventListener('mouseup',window.__rgraph_mouseup_event_listener_installed__,false);}else{var canvas=document.getElementById(id);canvas.removeEventListener('mouseup',canvas.__rgraph_mouseup_event_listener_installed__,false);canvas.removeEventListener('mousemove',canvas.__rgraph_mousemove_event_listener_installed__,false);canvas.removeEventListener('mousedown',canvas.__rgraph_mousedown_event_listener_installed__,false);canvas.removeEventListener('click',canvas.__rgraph_click_event_listener_installed__,false);}}
RGraph.HidePalette=function(){var div=RGraph.Registry.Get('palette');if(typeof(div)=='object'&&div){div.style.visibility='hidden';div.style.display='none';RGraph.Registry.Set('palette',null);}}
RGraph.random=function(min,max){var dp=arguments[2]?arguments[2]:0;var r=Math.random();return Number((((max-min)*r)+min).toFixed(dp));}
RGraph.NoShadow=function(obj){obj.context.shadowColor='rgba(0,0,0,0)';obj.context.shadowBlur=0;obj.context.shadowOffsetX=0;obj.context.shadowOffsetY=0;}
RGraph.SetShadow=function(obj,color,offsetx,offsety,blur){obj.context.shadowColor=color;obj.context.shadowOffsetX=offsetx;obj.context.shadowOffsetY=offsety;obj.context.shadowBlur=blur;}
RGraph.array_reverse=function(arr){var newarr=[];for(var i=arr.length-1;i>=0;i--){newarr.push(arr[i]);}return newarr;}
RGraph.Registry.Set=function(name,value){RGraph.Registry.store[name]=value;return value;}
RGraph.Registry.Get=function(name){return RGraph.Registry.store[name];}
RGraph.degrees2Radians=function(degrees){return degrees*(PI/180);}
RGraph.log=(function(n,base){var log=Math.log;return function(n,base){return log(n)/(base?log(base):1);};})();RGraph.is_array=function(obj){return obj!=null&&obj.constructor.toString().indexOf('Array')!=-1;}
RGraph.trim=function(str){return RGraph.ltrim(RGraph.rtrim(str));}
RGraph.ltrim=function(str){return str.replace(/^(\s|\0)+/,'');}
RGraph.rtrim=function(str){return str.replace(/(\s|\0)+$/,'');}
RGraph.GetHeight=function(obj){return obj.canvas.height;}
RGraph.GetWidth=function(obj){return obj.canvas.width;}
RGraph.is_null=function(arg){if(arg==null||(typeof(arg))=='object'&&!arg){return true;}return false;}
RGraph.Timer=function(label){if(typeof(RGraph.TIMER_LAST_CHECKPOINT)=='undefined'){RGraph.TIMER_LAST_CHECKPOINT=Date.now();}var now=Date.now();console.log(label+': '+(now-RGraph.TIMER_LAST_CHECKPOINT).toString());RGraph.TIMER_LAST_CHECKPOINT=now;}
RGraph.Async=function(func){return setTimeout(func,arguments[1]?arguments[1]:1);}
RGraph.isIE=function(){return navigator.userAgent.indexOf('MSIE')>0;};ISIE=RGraph.isIE();RGraph.isIE6=function(){return navigator.userAgent.indexOf('MSIE 6')>0;};ISIE6=RGraph.isIE6();RGraph.isIE7=function(){return navigator.userAgent.indexOf('MSIE 7')>0;};ISIE7=RGraph.isIE7();RGraph.isIE8=function(){return navigator.userAgent.indexOf('MSIE 8')>0;};ISIE8=RGraph.isIE8();RGraph.isIE9=function(){return navigator.userAgent.indexOf('MSIE 9')>0;};ISIE9=RGraph.isIE9();RGraph.isIE10=function(){return navigator.userAgent.indexOf('MSIE 10')>0;};ISIE10=RGraph.isIE10();RGraph.isIE9up=function(){navigator.userAgent.match(/MSIE (\d+)/);return Number(RegExp.$1)>=9;};ISIE9UP=RGraph.isIE9up();RGraph.isIE10up=function(){navigator.userAgent.match(/MSIE (\d+)/);return Number(RegExp.$1)>=10;};ISIE10UP=RGraph.isIE10up();RGraph.isOld=function(){return ISIE6||ISIE7||ISIE8;};ISOLD=RGraph.isOld();RGraph.Reset=function(canvas){canvas.width=canvas.width;RGraph.ObjectRegistry.Clear(canvas);canvas.__rgraph_aa_translated__=false;}
function pd(variable){RGraph.pr(variable);}
function p(variable){RGraph.pr(arguments[0],arguments[1],arguments[3]);}
function a(variable){alert(variable);}
function cl(variable){return console.log(variable);}


if(typeof(RGraph)=='undefined')RGraph={};RGraph.Bar=function(id,data)
{this.id=id;this.canvas=document.getElementById(id);this.context=this.canvas.getContext?this.canvas.getContext("2d"):null;this.canvas.__object__=this;this.type='bar';this.max=0;this.stackedOrGrouped=false;this.isRGraph=true;this.uid=RGraph.CreateUID();this.canvas.uid=this.canvas.uid?this.canvas.uid:RGraph.CreateUID();this.colorsParsed=false;RGraph.OldBrowserCompat(this.context);this.properties={'chart.background.barcolor1':'rgba(0,0,0,0)','chart.background.barcolor2':'rgba(0,0,0,0)','chart.background.grid':true,'chart.background.grid.color':'#ddd','chart.background.grid.width':1,'chart.background.grid.hsize':20,'chart.background.grid.vsize':20,'chart.background.grid.vlines':true,'chart.background.grid.hlines':true,'chart.background.grid.border':true,'chart.background.grid.autofit':true,'chart.background.grid.autofit.numhlines':5,'chart.background.grid.autofit.numvlines':20,'chart.background.image.stretch':true,'chart.background.image.x':null,'chart.background.image.y':null,'chart.background.image.w':null,'chart.background.image.h':null,'chart.background.image.align':null,'chart.ytickgap':20,'chart.smallyticks':3,'chart.largeyticks':5,'chart.numyticks':10,'chart.hmargin':5,'chart.hmargin.grouped':1,'chart.strokecolor':'rgba(0,0,0,0)','chart.axis.color':'black','chart.axis.linewidth':1,'chart.gutter.top':25,'chart.gutter.bottom':25,'chart.gutter.left':25,'chart.gutter.right':25,'chart.labels':null,'chart.labels.ingraph':null,'chart.labels.above':false,'chart.labels.above.decimals':0,'chart.labels.above.size':null,'chart.labels.above.angle':null,'chart.ylabels':true,'chart.ylabels.count':5,'chart.ylabels.inside':false,'chart.xlabels.offset':0,'chart.xaxispos':'bottom','chart.yaxispos':'left','chart.text.angle':0,'chart.text.color':'black','chart.text.size':10,'chart.text.font':'Arial','chart.ymin':0,'chart.ymax':null,'chart.title':'','chart.title.font':null,'chart.title.background':null,'chart.title.hpos':null,'chart.title.vpos':null,'chart.title.bold':true,'chart.title.xaxis':'','chart.title.xaxis.bold':true,'chart.title.xaxis.size':null,'chart.title.xaxis.font':null,'chart.title.yaxis':'','chart.title.yaxis.bold':true,'chart.title.yaxis.size':null,'chart.title.yaxis.font':null,'chart.title.yaxis.color':null,'chart.title.xaxis.pos':null,'chart.title.yaxis.pos':null,'chart.title.x':null,'chart.title.y':null,'chart.title.halign':null,'chart.title.valign':null,'chart.colors':['#01B4FF','#0f0','#00f','#ff0','#0ff','#0f0'],'chart.colors.sequential':false,'chart.colors.reverse':false,'chart.grouping':'grouped','chart.variant':'bar','chart.variant.sketch.verticals':true,'chart.shadow':false,'chart.shadow.color':'#999','chart.shadow.offsetx':3,'chart.shadow.offsety':3,'chart.shadow.blur':3,'chart.tooltips':null,'chart.tooltips.effect':'fade','chart.tooltips.css.class':'RGraph_tooltip','chart.tooltips.event':'onclick','chart.tooltips.highlight':true,'chart.highlight.stroke':'rgba(0,0,0,0)','chart.highlight.fill':'rgba(255,255,255,0.7)','chart.background.hbars':null,'chart.key':null,'chart.key.background':'white','chart.key.position':'graph','chart.key.shadow':false,'chart.key.shadow.color':'#666','chart.key.shadow.blur':3,'chart.key.shadow.offsetx':2,'chart.key.shadow.offsety':2,'chart.key.position.gutter.boxed':true,'chart.key.position.x':null,'chart.key.position.y':null,'chart.key.halign':'right','chart.key.color.shape':'square','chart.key.rounded':true,'chart.key.text.size':10,'chart.key.linewidth':1,'chart.key.colors':null,'chart.contextmenu':null,'chart.units.pre':'','chart.units.post':'','chart.scale.decimals':0,'chart.scale.point':'.','chart.scale.thousand':',','chart.crosshairs':false,'chart.crosshairs.color':'#333','chart.crosshairs.hline':true,'chart.crosshairs.vline':true,'chart.linewidth':1,'chart.annotatable':false,'chart.annotate.color':'black','chart.zoom.factor':1.5,'chart.zoom.fade.in':true,'chart.zoom.fade.out':true,'chart.zoom.hdir':'right','chart.zoom.vdir':'down','chart.zoom.frames':25,'chart.zoom.delay':16.666,'chart.zoom.shadow':true,'chart.zoom.background':true,'chart.resizable':false,'chart.resize.handle.adjust':[0,0],'chart.resize.handle.background':null,'chart.adjustable':false,'chart.noaxes':false,'chart.noxaxis':false,'chart.noyaxis':false,'chart.events.click':null,'chart.events.mousemove':null,'chart.numxticks':null,'chart.bevel':false}
if(!this.canvas){alert('[BAR] No canvas support');return;}
for(var i=0;i<data.length;++i){if(typeof(data[i])=='object'){this.stackedOrGrouped=true;}}
var linear_data=RGraph.array_linearize(data);for(var i=0;i<linear_data.length;++i){this['$'+i]={};}
this.data=data;this.coords=[];this.coords2=[];this.coordsText=[];this.data_arr=RGraph.array_linearize(this.data);if(!this.canvas.__rgraph_aa_translated__){this.context.translate(0.5,0.5);this.canvas.__rgraph_aa_translated__=true;}
RGraph.Register(this);}
RGraph.Bar.prototype.Set=function(name,value)
{name=name.toLowerCase();if(name.substr(0,6)!='chart.'){name='chart.'+name;}
if(name=='chart.labels.abovebar'){name='chart.labels.above';}
if(name=='chart.strokestyle'){name='chart.strokecolor';}
if(name=='chart.xaxispos'){if(value!='bottom'&&value!='center'&&value!='top'){alert('[BAR] ('+this.id+') chart.xaxispos should be top, center or bottom. Tried to set it to: '+value+' Changing it to center');value='center';}
if(value=='top'){for(var i=0;i<this.data.length;++i){if(typeof(this.data[i])=='number'&&this.data[i]>0){alert('[BAR] The data element with index '+i+' should be negative');}}}}
if(name.toLowerCase()=='chart.linewidth'&&value==0){value=0.0001;}
this.properties[name]=value;return this;}
RGraph.Bar.prototype.Get=function(name)
{if(name.substr(0,6)!='chart.'){name='chart.'+name;}
return this.properties[name];}
RGraph.Bar.prototype.Draw=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;if(typeof(prop['chart.background.image'])=='string'){RGraph.DrawBackgroundImage(this);}
RGraph.FireCustomEvent(this,'onbeforedraw');if(!this.colorsParsed){this.parseColors();this.colorsParsed=true;}
this.gutterLeft=prop['chart.gutter.left'];this.gutterRight=prop['chart.gutter.right'];this.gutterTop=prop['chart.gutter.top'];this.gutterBottom=prop['chart.gutter.bottom'];for(var i=0;i<this.data.length;++i){if(this.data[i]==null){this.data[i]=0;}}
if((prop['chart.variant']=='pyramid'||prop['chart.variant']=='dot')&&typeof(prop['chart.tooltips'])=='object'&&prop['chart.tooltips']&&prop['chart.tooltips'].length>0){alert('[BAR] ('+this.id+') Sorry, tooltips are not supported with dot or pyramid charts');}
this.coords=[];this.coords2=[];this.coordsText=[];this.max=0;this.grapharea=ca.height-this.gutterTop-this.gutterBottom;this.halfgrapharea=this.grapharea/2;this.halfTextHeight=prop['chart.text.size']/2;RGraph.background.Draw(this);if(prop['chart.variant']=='sketch'){this.DrawAxes();this.Drawbars();}else{this.Drawbars();this.DrawAxes();}
this.DrawLabels();if(prop['chart.bevel']||prop['chart.bevelled']){this.DrawBevel();}
if(prop['chart.key']&&prop['chart.key'].length){RGraph.DrawKey(this,prop['chart.key'],prop['chart.colors']);}
if(prop['chart.contextmenu']){RGraph.ShowContext(this);}
if(prop['chart.labels.ingraph']){RGraph.DrawInGraphLabels(this);}
if(prop['chart.resizable']){RGraph.AllowResizing(this);}
RGraph.InstallEventListeners(this);RGraph.FireCustomEvent(this,'ondraw');return this;}
RGraph.Bar.prototype.DrawAxes=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;if(prop['chart.noaxes']){return;}
var xaxispos=prop['chart.xaxispos'];var yaxispos=prop['chart.yaxispos'];var isSketch=prop['chart.variant']=='sketch';co.beginPath();co.strokeStyle=prop['chart.axis.color'];co.lineWidth=prop['chart.axis.linewidth']+0.001;if(navigator.userAgent.indexOf('Safari')==-1){co.lineCap='square';}
if(prop['chart.noyaxis']==false){if(yaxispos=='right'){co.moveTo(ca.width-this.gutterRight+(isSketch?3:0),this.gutterTop-(isSketch?3:0));co.lineTo(ca.width-this.gutterRight-(isSketch?2:0),ca.height-this.gutterBottom+(isSketch?5:0));}else{co.moveTo(this.gutterLeft-(isSketch?2:0),this.gutterTop-(isSketch?5:0));co.lineTo(this.gutterLeft-(isSketch?1:0),ca.height-this.gutterBottom+(isSketch?5:0));}}
if(prop['chart.noxaxis']==false){if(xaxispos=='center'){co.moveTo(this.gutterLeft-(isSketch?5:0),Math.round(((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop+(isSketch?2:0)));co.lineTo(ca.width-this.gutterRight+(isSketch?5:0),Math.round(((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop-(isSketch?2:0)));}else if(xaxispos=='top'){co.moveTo(this.gutterLeft-(isSketch?3:0),this.gutterTop-(isSketch?3:0));co.lineTo(ca.width-this.gutterRight+(isSketch?5:0),this.gutterTop+(isSketch?2:0));}else{co.moveTo(this.gutterLeft-(isSketch?5:0),ca.height-this.gutterBottom-(isSketch?2:0));co.lineTo(ca.width-this.gutterRight+(isSketch?8:0),ca.height-this.gutterBottom+(isSketch?2:0));}}
var numYTicks=prop['chart.numyticks'];if(prop['chart.noyaxis']==false&&!isSketch){var yTickGap=(ca.height-this.gutterTop-this.gutterBottom)/numYTicks;var xpos=yaxispos=='left'?this.gutterLeft:ca.width-this.gutterRight;if(this.properties['chart.numyticks']>0){for(y=this.gutterTop;xaxispos=='center'?y<=(ca.height-this.gutterBottom):y<(ca.height-this.gutterBottom+(xaxispos=='top'?1:0));y+=yTickGap){if(xaxispos=='center'&&y==(this.gutterTop+(this.grapharea/2)))continue;if(xaxispos=='top'&&y==this.gutterTop)continue;co.moveTo(xpos+(yaxispos=='left'?0:0),Math.round(y));co.lineTo(xpos+(yaxispos=='left'?-3:3),Math.round(y));}}
if(prop['chart.noxaxis']){if(xaxispos=='center'){co.moveTo(xpos+(yaxispos=='left'?-3:3),Math.round(ca.height/2));co.lineTo(xpos,Math.round(ca.height/2));}else if(xaxispos=='top'){co.moveTo(xpos+(yaxispos=='left'?-3:3),Math.round(this.gutterTop));co.lineTo(xpos,Math.round(this.gutterTop));}else{co.moveTo(xpos+(yaxispos=='left'?-3:3),Math.round(ca.height-this.gutterBottom));co.lineTo(xpos,Math.round(ca.height-this.gutterBottom));}}}
if(prop['chart.noxaxis']==false&&!isSketch){if(typeof(prop['chart.numxticks'])=='number'){var xTickGap=(ca.width-this.gutterLeft-this.gutterRight)/prop['chart.numxticks'];}else{var xTickGap=(ca.width-this.gutterLeft-this.gutterRight)/this.data.length;}
if(xaxispos=='bottom'){yStart=ca.height-this.gutterBottom;yEnd=(ca.height-this.gutterBottom)+3;}else if(xaxispos=='top'){yStart=this.gutterTop-3;yEnd=this.gutterTop;}else if(xaxispos=='center'){yStart=((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop+3;yEnd=((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop-3;}
yStart=yStart;yEnd=yEnd;var noEndXTick=prop['chart.noendxtick'];for(x=this.gutterLeft+(yaxispos=='left'?xTickGap:0),len=(ca.width-this.gutterRight+(yaxispos=='left'?5:0));x<len;x+=xTickGap){if(yaxispos=='left'&&!noEndXTick&&x>this.gutterLeft){co.moveTo(Math.round(x),yStart);co.lineTo(Math.round(x),yEnd);}else if(yaxispos=='left'&&noEndXTick&&x>this.gutterLeft&&x<(ca.width-this.gutterRight)){co.moveTo(Math.round(x),yStart);co.lineTo(Math.round(x),yEnd);}else if(yaxispos=='right'&&x<(ca.width-this.gutterRight)&&!noEndXTick){co.moveTo(Math.round(x),yStart);co.lineTo(Math.round(x),yEnd);}else if(yaxispos=='right'&&x<(ca.width-this.gutterRight)&&x>(this.gutterLeft)&&noEndXTick){co.moveTo(Math.round(x),yStart);co.lineTo(Math.round(x),yEnd);}}
if(prop['chart.noyaxis']||prop['chart.numxticks']==null){if(typeof(prop['chart.numxticks'])=='number'&&prop['chart.numxticks']>0){co.moveTo(Math.round(this.gutterLeft),yStart);co.lineTo(Math.round(this.gutterLeft),yEnd);}}}
if(prop['chart.noyaxis']&&prop['chart.noxaxis']==false&&prop['chart.numxticks']==null){if(xaxispos=='center'){co.moveTo(Math.round(this.gutterLeft),(ca.height/2)-3);co.lineTo(Math.round(this.gutterLeft),(ca.height/2)+3);}else{co.moveTo(Math.round(this.gutterLeft),ca.height-this.gutterBottom);co.lineTo(Math.round(this.gutterLeft),ca.height-this.gutterBottom+3);}}
this.context.stroke();}
RGraph.Bar.prototype.Drawbars=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;co.lineWidth=prop['chart.linewidth'];co.strokeStyle=prop['chart.strokecolor'];co.fillStyle=prop['chart.colors'][0];var prevX=0;var prevY=0;var decimals=prop['chart.scale.decimals'];if(prop['chart.ymax']){this.scale2=RGraph.getScale2(this,{'max':prop['chart.ymax'],'strict':true,'min':prop['chart.ymin'],'scale.thousand':prop['chart.scale.thousand'],'scale.point':prop['chart.scale.point'],'scale.decimals':prop['chart.scale.decimals'],'ylabels.count':prop['chart.ylabels.count'],'scale.round':prop['chart.scale.round'],'units.pre':prop['chart.units.pre'],'units.post':prop['chart.units.post']});}else{for(i=0;i<this.data.length;++i){if(typeof(this.data[i])=='object'){var value=prop['chart.grouping']=='grouped'?Number(RGraph.array_max(this.data[i],true)):Number(RGraph.array_sum(this.data[i]));}else{var value=Number(this.data[i]);}
this.max=Math.max(Math.abs(this.max),Math.abs(value));}
this.scale2=RGraph.getScale2(this,{'max':this.max,'min':prop['chart.ymin'],'scale.thousand':prop['chart.scale.thousand'],'scale.point':prop['chart.scale.point'],'scale.decimals':prop['chart.scale.decimals'],'ylabels.count':prop['chart.ylabels.count'],'scale.round':prop['chart.scale.round'],'units.pre':prop['chart.units.pre'],'units.post':prop['chart.units.post']});}
if(prop['chart.adjustable']&&!prop['chart.ymax']){this.Set('chart.ymax',this.scale2.max);}
if(prop['chart.background.hbars']&&prop['chart.background.hbars'].length>0){RGraph.DrawBars(this);}
var variant=prop['chart.variant'];if(variant=='3d'){RGraph.Draw3DAxes(this);}
var xaxispos=prop['chart.xaxispos'];var width=(this.canvas.width-this.gutterLeft-this.gutterRight)/this.data.length;var orig_height=height;var hmargin=prop['chart.hmargin'];var shadow=prop['chart.shadow'];var shadowColor=prop['chart.shadow.color'];var shadowBlur=prop['chart.shadow.blur'];var shadowOffsetX=prop['chart.shadow.offsetx'];var shadowOffsetY=prop['chart.shadow.offsety'];var strokeStyle=prop['chart.strokecolor'];var colors=prop['chart.colors'];var sequentialColorIndex=0;for(i=0;i<this.data.length;++i){var height=((RGraph.array_sum(this.data[i])<0?RGraph.array_sum(this.data[i])+this.scale2.min:RGraph.array_sum(this.data[i])-this.scale2.min)/(this.scale2.max-this.scale2.min))*(ca.height-this.gutterTop-this.gutterBottom);if(xaxispos=='center'){height/=2;}
var x=(i*width)+this.gutterLeft;var y=xaxispos=='center'?((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop-height:ca.height-height-this.gutterBottom;if(xaxispos=='top'){y=this.gutterTop+Math.abs(height);}
if(height<0){y+=height;height=Math.abs(height);}
if(shadow){co.shadowColor=shadowColor;co.shadowBlur=shadowBlur;co.shadowOffsetX=shadowOffsetX;co.shadowOffsetY=shadowOffsetY;}
this.context.beginPath();if(typeof(this.data[i])=='number'){var barWidth=width-(2*hmargin);if(barWidth<0){alert('[RGRAPH] Warning: you have a negative bar width. This may be caused by the chart.hmargin being too high or the width of the canvas not being sufficient.');}
co.strokeStyle=strokeStyle;co.fillStyle=colors[0];if(prop['chart.colors.sequential']){co.fillStyle=colors[i];}
if(variant=='sketch'){this.context.lineCap='round';var sketchOffset=3;co.beginPath();co.strokeStyle=colors[0];if(prop['chart.colors.sequential']){co.strokeStyle=colors[i];}
co.moveTo(x+hmargin+2,y+height-2);co.lineTo(x+hmargin-1,y-4);co.moveTo(x+hmargin-3,y+-2+(this.data[i]<0?height:0));co.bezierCurveTo(x+((hmargin+width)*0.33),y+15+(this.data[i]<0?height-10:0),x+((hmargin+width)*0.66),y+5+(this.data[i]<0?height-10:0),x+hmargin+width+-1,y+0+(this.data[i]<0?height:0));co.moveTo(x+hmargin+width-5,y-5);co.lineTo(x+hmargin+width-3,y+height-3);if(prop['chart.variant.sketch.verticals']){for(var r=0.2;r<=0.8;r+=0.2){co.moveTo(x+hmargin+width+(r>0.4?-1:3)-(r*width),y-1);co.lineTo(x+hmargin+width-(r>0.4?1:-1)-(r*width),y+height+(r==0.2?1:-2));}}
this.context.stroke();}else if(variant=='bar'||variant=='3d'||variant=='glass'||variant=='bevel'){if(RGraph.isOld()&&shadow){this.DrawIEShadow([x+hmargin,y,barWidth,height]);}
if(variant=='glass'){RGraph.filledCurvyRect(this.context,x+hmargin,y,barWidth,height,3,this.data[i]>0,this.data[i]>0,this.data[i]<0,this.data[i]<0);RGraph.strokedCurvyRect(this.context,x+hmargin,y,barWidth,height,3,this.data[i]>0,this.data[i]>0,this.data[i]<0,this.data[i]<0);}else{this.context.strokeRect(x+hmargin,y,barWidth,height);this.context.fillRect(x+hmargin,y,barWidth,height);}
if(variant=='3d'){var prevStrokeStyle=co.strokeStyle;var prevFillStyle=co.fillStyle;co.beginPath();co.moveTo(x+hmargin,y);co.lineTo(x+hmargin+10,y-5);co.lineTo(x+hmargin+10+barWidth,y-5);co.lineTo(x+hmargin+barWidth,y);co.closePath();co.stroke();co.fill();co.beginPath();co.moveTo(x+hmargin+barWidth,y);co.lineTo(x+hmargin+barWidth+10,y-5);co.lineTo(x+hmargin+barWidth+10,y+height-5);co.lineTo(x+hmargin+barWidth,y+height);co.closePath();co.stroke();co.fill();co.beginPath();co.fillStyle='rgba(255,255,255,0.3)';co.moveTo(x+hmargin,y);co.lineTo(x+hmargin+10,y-5);co.lineTo(x+hmargin+10+barWidth,y-5);co.lineTo(x+hmargin+barWidth,y);co.lineTo(x+hmargin,y);co.closePath();co.stroke();co.fill();co.beginPath();co.fillStyle='rgba(0,0,0,0.4)';co.moveTo(x+hmargin+barWidth,y);co.lineTo(x+hmargin+barWidth+10,y-5);co.lineTo(x+hmargin+barWidth+10,y-5+height);co.lineTo(x+hmargin+barWidth,y+height);co.lineTo(x+hmargin+barWidth,y);co.closePath();co.stroke();co.fill();co.strokeStyle=prevStrokeStyle;co.fillStyle=prevFillStyle;}else if(variant=='glass'){var grad=co.createLinearGradient(x+hmargin,y,x+hmargin+(barWidth/2),y);grad.addColorStop(0,'rgba(255,255,255,0.9)');grad.addColorStop(1,'rgba(255,255,255,0.5)');co.beginPath();co.fillStyle=grad;co.fillRect(x+hmargin+2,y+(this.data[i]>0?2:0),(barWidth/2)-2,height-2);co.fill();}
if(prop['chart.labels.above']){if(shadow){RGraph.NoShadow(this);}
var yPos=y-3;var xPos=x+hmargin+(barWidth/2);if(this.data[i]<0){yPos+=height+6+(prop['chart.text.size']);}
if(prop['chart.xaxispos']=='top'){yPos=this.gutterTop+height+6+(typeof(prop['chart.labels.above.size'])=='number'?prop['chart.labels.above.size']:prop['chart.text.size']-4);}
if(prop['chart.variant']=='3d'){yPos-=3;xPos+=5;}
if(this.properties['chart.labels.above.angle']){var angle=-45;var halign='left';var valign='center';}else{var angle=0;var halign='center';var valign='bottom';}
co.fillStyle=prop['chart.text.color'];RGraph.Text2(this,{'font':prop['chart.text.font'],'size':typeof(prop['chart.labels.above.size'])=='number'?prop['chart.labels.above.size']:prop['chart.text.size']-3,'x':xPos,'y':yPos,'text':RGraph.number_format(this,Number(this.data[i]).toFixed(prop['chart.labels.above.decimals']),prop['chart.units.pre'],prop['chart.units.post']),'halign':halign,'marker':false,'valign':valign,'angle':angle,'tag':'labels.above'});}}else if(variant=='dot'){co.beginPath();co.moveTo(x+(width/2),y);co.lineTo(x+(width/2),y+height);co.stroke();co.beginPath();co.fillStyle=this.properties['chart.colors'][i];co.arc(x+(width/2),y+(this.data[i]>0?0:height),2,0,6.28,0);co.fillStyle=prop['chart.colors'][0];if(prop['chart.colors.sequential']){co.fillStyle=colors[i];}
co.stroke();co.fill();}else{alert('[BAR] Warning! Unknown chart.variant: '+variant);}
this.coords.push([x+hmargin,y,width-(2*hmargin),height]);if(typeof this.coords2[i]=='undefined'){this.coords2[i]=[];}
this.coords2[i].push([x+hmargin,y,width-(2*hmargin),height]);}else if(typeof(this.data[i])=='object'&&prop['chart.grouping']=='stacked'){if(this.scale2.min){alert("[ERROR] Stacked Bar charts with a Y min are not supported");}
var barWidth=width-(2*hmargin);var redrawCoords=[];var startY=0;var dataset=this.data[i];if(barWidth<0){alert('[RGRAPH] Warning: you have a negative bar width. This may be caused by the chart.hmargin being too high or the width of the canvas not being sufficient.');}
for(j=0;j<dataset.length;++j){if(xaxispos=='center'){alert("[BAR] It's pointless having the X axis position at the center on a stacked bar chart.");return;}
if(this.data[i][j]<0){alert('[BAR] Negative values are not permitted with a stacked bar chart. Try a grouped one instead.');return;}
co.strokeStyle=strokeStyle
co.fillStyle=colors[j];if(prop['chart.colors.reverse']){co.fillStyle=colors[this.data[i].length-j-1];}
if(prop['chart.colors.sequential']&&colors[sequentialColorIndex]){co.fillStyle=colors[sequentialColorIndex++];}else if(prop['chart.colors.sequential']){co.fillStyle=colors[sequentialColorIndex-1];}
var height=(dataset[j]/this.scale2.max)*(ca.height-this.gutterTop-this.gutterBottom);if(xaxispos=='center'){height/=2;}
var totalHeight=(RGraph.array_sum(dataset)/this.scale2.max)*(ca.height-hmargin-this.gutterTop-this.gutterBottom);this.coords.push([x+hmargin,y,width-(2*hmargin),height]);if(typeof this.coords2[i]=='undefined'){this.coords2[i]=[];}
this.coords2[i].push([x+hmargin,y,width-(2*hmargin),height]);if(RGraph.isOld()&&shadow){this.DrawIEShadow([x+hmargin,y,width-(2*hmargin),height+1]);}
co.strokeRect(x+hmargin,y,width-(2*hmargin),height);co.fillRect(x+hmargin,y,width-(2*hmargin),height);if(j==0){var startY=y;var startX=x;}
if(shadow){redrawCoords.push([x+hmargin,y,width-(2*hmargin),height,co.fillStyle]);}
if(variant=='3d'){var prevFillStyle=co.fillStyle;var prevStrokeStyle=co.strokeStyle;if(j==0){co.beginPath();co.moveTo(startX+hmargin,y);co.lineTo(startX+10+hmargin,y-5);co.lineTo(startX+10+barWidth+hmargin,y-5);co.lineTo(startX+barWidth+hmargin,y);co.closePath();co.fill();co.stroke();}
co.beginPath();co.moveTo(startX+barWidth+hmargin,y);co.lineTo(startX+barWidth+hmargin+10,y-5);co.lineTo(startX+barWidth+hmargin+10,y-5+height);co.lineTo(startX+barWidth+hmargin,y+height);co.closePath();co.fill();co.stroke();if(j==0){co.fillStyle='rgba(255,255,255,0.3)';co.beginPath();co.moveTo(startX+hmargin,y);co.lineTo(startX+10+hmargin,y-5);co.lineTo(startX+10+barWidth+hmargin,y-5);co.lineTo(startX+barWidth+hmargin,y);co.closePath();co.fill();co.stroke();}
co.fillStyle='rgba(0,0,0,0.4)';co.beginPath();co.moveTo(startX+barWidth+hmargin,y);co.lineTo(startX+barWidth+hmargin+10,y-5);co.lineTo(startX+barWidth+hmargin+10,y-5+height);co.lineTo(startX+barWidth+hmargin,y+height);co.closePath();co.fill();co.stroke();co.strokeStyle=prevStrokeStyle;co.fillStyle=prevFillStyle;}
y+=height;}
if(this.Get('chart.labels.above')){RGraph.NoShadow(this);co.fillStyle=prop['chart.text.color'];if(this.properties['chart.labels.above.angle']){var angle=-45;var halign='left';var valign='center';}else{var angle=0;var halign='center';var valign='bottom';}
RGraph.Text2(this,{'font':prop['chart.text.font'],'size':typeof(prop['chart.labels.above.size'])=='number'?prop['chart.labels.above.size']:prop['chart.text.size']-3,'x':startX+(barWidth/2)+prop['chart.hmargin'],'y':startY-(prop['chart.shadow']&&this.properties['chart.shadow.offsety']<0?7:4)-(prop['chart.variant']=='3d'?5:0),'text':String(prop['chart.units.pre']+RGraph.array_sum(this.data[i]).toFixed(prop['chart.labels.above.decimals'])+prop['chart.units.post']),'angle':angle,'valign':valign,'halign':halign,'tag':'labels.above'});if(shadow){co.shadowColor=shadowColor;co.shadowBlur=shadowBlur;co.shadowOffsetX=shadowOffsetX;co.shadowOffsetY=shadowOffsetY;}}
if(shadow){RGraph.NoShadow(this);for(k=0;k<redrawCoords.length;++k){co.strokeStyle=strokeStyle;co.fillStyle=redrawCoords[k][4];co.strokeRect(redrawCoords[k][0],redrawCoords[k][1],redrawCoords[k][2],redrawCoords[k][3]);co.fillRect(redrawCoords[k][0],redrawCoords[k][1],redrawCoords[k][2],redrawCoords[k][3]);co.stroke();co.fill();}
redrawCoords=[];}}else if(typeof(this.data[i])=='object'&&prop['chart.grouping']=='grouped'){var redrawCoords=[];co.lineWidth=prop['chart.linewidth'];for(j=0;j<this.data[i].length;++j){co.strokeStyle=strokeStyle;co.fillStyle=colors[j];if(prop['chart.colors.sequential']&&colors[sequentialColorIndex]){co.fillStyle=colors[sequentialColorIndex++];}else if(prop['chart.colors.sequential']){co.fillStyle=colors[sequentialColorIndex-1];}
var individualBarWidth=(width-(2*hmargin))/this.data[i].length;var height=((this.data[i][j]+(this.data[i][j]<0?this.scale2.min:(-1*this.scale2.min)))/(this.scale2.max-this.scale2.min))*(ca.height-this.gutterTop-this.gutterBottom);var groupedMargin=prop['chart.hmargin.grouped'];var startX=x+hmargin+(j*individualBarWidth);if(individualBarWidth<0){alert('[RGRAPH] Warning: you have a negative bar width. This may be caused by the chart.hmargin being too high or the width of the canvas not being sufficient.');}
if(xaxispos=='center'){height/=2;}
if(xaxispos=='top'){var startY=this.gutterTop;var height=Math.abs(height);}else if(xaxispos=='center'){var startY=this.gutterTop+(this.grapharea/2)-height;}else{var startY=ca.height-this.gutterBottom-height;var height=Math.abs(height);}
if(RGraph.isOld()&&shadow){this.DrawIEShadow([startX,startY,individualBarWidth,height]);}
co.strokeRect(startX+groupedMargin,startY,individualBarWidth-(2*groupedMargin),height);co.fillRect(startX+groupedMargin,startY,individualBarWidth-(2*groupedMargin),height);y+=height;if(variant=='3d'){var prevFillStyle=co.fillStyle;var prevStrokeStyle=co.strokeStyle;co.beginPath();co.moveTo(startX,startY);co.lineTo(startX+10,startY-5);co.lineTo(startX+10+individualBarWidth,startY-5);co.lineTo(startX+individualBarWidth,startY);co.closePath();co.fill();co.stroke();co.beginPath();co.moveTo(startX+individualBarWidth,startY);co.lineTo(startX+individualBarWidth+10,startY-5);co.lineTo(startX+individualBarWidth+10,startY-5+height);co.lineTo(startX+individualBarWidth,startY+height);co.closePath();co.fill();co.stroke();co.fillStyle='rgba(255,255,255,0.3)';co.beginPath();co.moveTo(startX,startY);co.lineTo(startX+10,startY-5);co.lineTo(startX+10+individualBarWidth,startY-5);co.lineTo(startX+individualBarWidth,startY);co.closePath();co.fill();co.stroke();co.fillStyle='rgba(0,0,0,0.4)';co.beginPath();co.moveTo(startX+individualBarWidth,startY);co.lineTo(startX+individualBarWidth+10,startY-5);co.lineTo(startX+individualBarWidth+10,startY-5+height);co.lineTo(startX+individualBarWidth,startY+height);co.closePath();co.fill();co.stroke();co.strokeStyle=prevStrokeStyle;co.fillStyle=prevFillStyle;}
if(height<0){height=Math.abs(height);startY=startY-height;}
this.coords.push([startX+groupedMargin,startY,individualBarWidth-(2*groupedMargin),height]);if(typeof this.coords2[i]=='undefined'){this.coords2[i]=[];}
this.coords2[i].push([x+hmargin+groupedMargin,y,width-(2*hmargin)-(2*groupedMargin),height]);if(prop['chart.shadow']){redrawCoords.push([startX+groupedMargin,startY,individualBarWidth-(2*groupedMargin),height,co.fillStyle]);}
if(prop['chart.labels.above']){co.strokeStyle='rgba(0,0,0,0)';if(shadow){RGraph.NoShadow(this);}
var yPos=y-3;if(prop['chart.labels.above.angle']){var angle=-45;var halign='left';var valign='center';}else{var angle=0;var halign='center';var valign='bottom';if(this.data[i][j]<0||prop['chart.xaxispos']=='top'){yPos=startY+height+6;var valign='top';}else{yPos=startY;}}
co.fillStyle=prop['chart.text.color'];RGraph.Text2(this,{'font':prop['chart.text.font'],'size':typeof(prop['chart.labels.above.size'])=='number'?prop['chart.labels.above.size']:prop['chart.text.size']-3,'x':startX+(individualBarWidth/2),'y':yPos-3,'text':RGraph.number_format(this,this.data[i][j].toFixed(prop['chart.labels.above.decimals'])),'halign':halign,'valign':valign,'angle':angle,'tag':'labels.above'});if(shadow){co.shadowColor=shadowColor;co.shadowBlur=shadowBlur;co.shadowOffsetX=shadowOffsetX;co.shadowOffsetY=shadowOffsetY;}}}
if(redrawCoords.length){RGraph.NoShadow(this);co.lineWidth=prop['chart.linewidth'];co.beginPath();for(var j=0;j<redrawCoords.length;++j){co.fillStyle=redrawCoords[j][4];co.strokeStyle=prop['chart.strokecolor'];co.fillRect(redrawCoords[j][0],redrawCoords[j][1],redrawCoords[j][2],redrawCoords[j][3]);co.strokeRect(redrawCoords[j][0],redrawCoords[j][1],redrawCoords[j][2],redrawCoords[j][3]);}
co.fill();co.stroke();redrawCoords=[];}}
co.closePath();}
RGraph.NoShadow(this);}
RGraph.Bar.prototype.DrawLabels=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;var context=co;var text_angle=prop['chart.text.angle'];var text_size=prop['chart.text.size'];var labels=prop['chart.labels'];if(prop['chart.ylabels']){if(prop['chart.xaxispos']=='top')this.Drawlabels_top();if(prop['chart.xaxispos']=='center')this.Drawlabels_center();if(prop['chart.xaxispos']=='bottom')this.Drawlabels_bottom();}
if(typeof(labels)=='object'&&labels){var yOffset=Number(prop['chart.xlabels.offset']);if(prop['chart.text.angle']!=0){var valign='center';var halign='right';var angle=0-prop['chart.text.angle'];}else{var valign='top';var halign='center';var angle=0;}
co.fillStyle=prop['chart.text.color'];var barWidth=(ca.width-this.gutterRight-this.gutterLeft)/labels.length;xTickGap=(ca.width-this.gutterRight-this.gutterLeft)/labels.length
var i=0;var font=prop['chart.text.font'];for(x=this.gutterLeft+(xTickGap/2);x<=ca.width-this.gutterRight;x+=xTickGap){RGraph.Text2(this,{'font':font,'size':text_size,'x':x,'y':prop['chart.xaxispos']=='top'?this.gutterTop-yOffset-5:(ca.height-this.gutterBottom)+yOffset+3,'text':String(labels[i++]),'valign':prop['chart.xaxispos']=='top'?'bottom':valign,'halign':halign,'tag':'label','marker':false,'angle':angle,'tag':'labels'});}}
if(prop['chart.labels.above.specific']){var labels=prop['chart.labels.above.specific'];for(var i=0;i<this.coords.length;++i){var xaxispos=prop['chart.xaxispos'];var coords=this.coords[i];var value=this.data_arr[i];var valign=(value>=0&&xaxispos!='top')?'bottom':'top';var halign='center';var text=labels[i];if(text&&text.toString().length>0){RGraph.Text2(this,{'font':prop['chart.text.font'],'size':prop['chart.labels.above.size']?prop['chart.labels.above.size']:prop['chart.text.size'],'x':coords[0]+(coords[2]/2),'y':(value>=0&&xaxispos!='top')?coords[1]-5:coords[1]+coords[3]+3,'text':String(labels[i]),'valign':valign,'halign':halign,'tag':'labels.above'});}}}}
RGraph.Bar.prototype.Drawlabels_top=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;co.beginPath();co.fillStyle=prop['chart.text.color'];co.strokeStyle='black';if(prop['chart.xaxispos']=='top'){var context=co;var text_size=prop['chart.text.size'];var units_pre=prop['chart.units.pre'];var units_post=prop['chart.units.post'];var align=prop['chart.yaxispos']=='left'?'right':'left';var font=prop['chart.text.font'];var numYLabels=prop['chart.ylabels.count'];var ymin=prop['chart.ymin'];if(prop['chart.ylabels.inside']==true){var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft+5:ca.width-this.gutterRight-5;var align=prop['chart.yaxispos']=='left'?'left':'right';var boxed=true;}else{var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft-5:ca.width-this.gutterRight+5;var boxed=false;}
if(typeof(prop['chart.ylabels.specific'])=='object'&&prop['chart.ylabels.specific']){var labels=RGraph.array_reverse(prop['chart.ylabels.specific']);var grapharea=ca.height-this.gutterTop-this.gutterBottom;for(var i=0;i<labels.length;++i){var y=this.gutterTop+(grapharea*(i/labels.length))+(grapharea/labels.length);RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':String(labels[i]),'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
return;}
var labels=this.scale2.labels;for(var i=0;i<labels.length;++i){RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':this.gutterTop+((this.grapharea/labels.length)*(i+1)),'text':'-'+labels[i],'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
if(prop['chart.ymin']!=0||prop['chart.noxaxis']){RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':this.gutterTop,'text':(this.scale2.min!=0?'-':'')+RGraph.number_format(this,(this.scale2.min.toFixed((prop['chart.scale.decimals']))),units_pre,units_post),'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}}
co.fill();}
RGraph.Bar.prototype.Drawlabels_center=function()
{var ca=this.canvas;var co=this.context;var prop=this.properties;var font=prop['chart.text.font'];var numYLabels=prop['chart.ylabels.count'];co.fillStyle=prop['chart.text.color'];if(prop['chart.xaxispos']=='center'){var text_size=prop['chart.text.size'];var units_pre=prop['chart.units.pre'];var units_post=prop['chart.units.post'];var context=co;var align='';var xpos=0;var boxed=false;var ymin=prop['chart.ymin'];co.fillStyle=prop['chart.text.color'];co.strokeStyle='black';if(prop['chart.ylabels.inside']==true){var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft+5:ca.width-this.gutterRight-5;var align=prop['chart.yaxispos']=='left'?'left':'right';var boxed=true;}else{var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft-5:ca.width-this.gutterRight+5;var align=prop['chart.yaxispos']=='left'?'right':'left';var boxed=false;}
if(typeof(prop['chart.ylabels.specific'])=='object'&&prop['chart.ylabels.specific']){var labels=prop['chart.ylabels.specific'];var grapharea=ca.height-this.gutterTop-this.gutterBottom;for(var i=0;i<labels.length;++i){var y=this.gutterTop+((grapharea/2)/(labels.length-1))*i;RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':String(labels[i]),'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
for(var i=labels.length-1;i>=1;--i){var y=this.gutterTop+(grapharea*(i/((labels.length-1)*2)))+(grapharea/2);RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':String(labels[labels.length-i-1]),'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
return;}
for(var i=0;i<this.scale2.labels.length;++i){var y=this.gutterTop+this.halfgrapharea-((this.halfgrapharea/numYLabels)*(i+1));var text=this.scale2.labels[i];RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':text,'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
for(var i=(this.scale2.labels.length-1);i>=0;--i){var y=this.gutterTop+((this.halfgrapharea/numYLabels)*(i+1))+this.halfgrapharea;var text=this.scale2.labels[i];RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':'-'+text,'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
if(this.scale2.min!=0){RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':this.gutterTop+this.halfgrapharea,'text':RGraph.number_format(this,(this.scale2.min.toFixed((this.properties['chart.scale.decimals']))),units_pre,units_post),'valign':'center','valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}}}
RGraph.Bar.prototype.Drawlabels_bottom=function()
{var co=this.context;var ca=this.canvas;var prop=this.properties;var text_size=prop['chart.text.size'];var units_pre=prop['chart.units.pre'];var units_post=prop['chart.units.post'];var context=this.context;var align=prop['chart.yaxispos']=='left'?'right':'left';var font=prop['chart.text.font'];var numYLabels=prop['chart.ylabels.count'];var ymin=prop['chart.ymin'];co.beginPath();co.fillStyle=this.properties['chart.text.color'];co.strokeStyle='black';if(prop['chart.ylabels.inside']==true){var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft+5:ca.width-this.gutterRight-5;var align=prop['chart.yaxispos']=='left'?'left':'right';var boxed=true;}else{var xpos=prop['chart.yaxispos']=='left'?this.gutterLeft-5:ca.width-this.gutterRight+5;var boxed=false;}
if(prop['chart.ylabels.specific']&&typeof(prop['chart.ylabels.specific'])=='object'){var labels=prop['chart.ylabels.specific'];var grapharea=ca.height-this.gutterTop-this.gutterBottom;for(var i=0;i<labels.length;++i){var y=this.gutterTop+(grapharea*(i/(labels.length-1)));RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':y,'text':labels[i],'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
return;}
var gutterTop=this.gutterTop;var halfTextHeight=this.halfTextHeight;var scale=this.scale;for(var i=0;i<numYLabels;++i){var text=this.scale2.labels[i];RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':this.gutterTop+this.grapharea-((this.grapharea/numYLabels)*(i+1)),'text':text,'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
if(this.properties['chart.ymin']!=0||this.properties['chart.noxaxis']){RGraph.Text2(this,{'font':font,'size':text_size,'x':xpos,'y':this.canvas.height-this.gutterBottom,'text':RGraph.number_format(this,(this.scale2.min.toFixed((this.properties['chart.scale.decimals']))),units_pre,units_post),'valign':'center','halign':align,'bordered':boxed,'tag':'scale'});}
co.fill();}
RGraph.Bar.prototype.DrawIEShadow=function(coords)
{var co=this.context;var ca=this.canvas;var prop=this.properties;var prevFillStyle=co.fillStyle;var offsetx=prop['chart.shadow.offsetx'];var offsety=prop['chart.shadow.offsety'];co.lineWidth=prop['chart.linewidth'];co.fillStyle=prop['chart.shadow.color'];co.beginPath();co.fillRect(coords[0]+offsetx,coords[1]+offsety,coords[2],coords[3]);co.fill();co.fillStyle=prevFillStyle;}
RGraph.Bar.prototype.getShape=RGraph.Bar.prototype.getBar=function(e)
{var obj=arguments[1]?arguments[1]:this;var mouseXY=RGraph.getMouseXY(e);var mouseX=mouseXY[0];var mouseY=mouseXY[1];var canvas=obj.canvas;var context=obj.context;var coords=obj.coords
for(var i=0;i<coords.length;i++){var left=coords[i][0];var top=coords[i][1];var width=coords[i][2];var height=coords[i][3];var prop=obj.properties;if(mouseX>=left&&mouseX<=(left+width)&&mouseY>=top&&mouseY<=(top+height)){if(prop['chart.tooltips']){var tooltip=RGraph.parseTooltipText?RGraph.parseTooltipText(prop['chart.tooltips'],i):prop['chart.tooltips'][i];}
var dataset=0;var idx=i;while(idx>=(typeof(obj.data[dataset])=='object'?obj.data[dataset].length:1)){if(typeof(obj.data[dataset])=='number'){idx-=1;}else{idx-=obj.data[dataset].length;}
dataset++;}
if(typeof(obj.data[dataset])=='number'){idx=null;}
return{0:obj,1:left,2:top,3:width,4:height,5:i,'object':obj,'x':left,'y':top,'width':width,'height':height,'index':i,'tooltip':tooltip,'index_adjusted':idx,'dataset':dataset};}}
return null;}
RGraph.Bar.prototype.getShapeByX=function(e)
{var canvas=e.target;var mouseCoords=RGraph.getMouseXY(e);var obj=arguments[1]?arguments[1]:this;for(var i=0;i<obj.coords.length;i++){var mouseX=mouseCoords[0];var mouseY=mouseCoords[1];var left=obj.coords[i][0];var top=obj.coords[i][1];var width=obj.coords[i][2];var height=obj.coords[i][3];var prop=obj.properties;if(mouseX>=left&&mouseX<=(left+width)){if(prop['chart.tooltips']){var tooltip=RGraph.parseTooltipText?RGraph.parseTooltipText(prop['chart.tooltips'],i):prop['chart.tooltips'][i];}
return{0:obj,1:left,2:top,3:width,4:height,5:i,'object':obj,'x':left,'y':top,'width':width,'height':height,'index':i,'tooltip':tooltip};}}
return null;}
RGraph.Bar.prototype.getValue=function(arg)
{var co=this.context;var ca=this.canvas;var prop=this.properties;if(arg.length==2){var mouseX=arg[0];var mouseY=arg[1];}else{var mouseCoords=RGraph.getMouseXY(arg);var mouseX=mouseCoords[0];var mouseY=mouseCoords[1];}
if(mouseY<prop['chart.gutter.top']||mouseY>(ca.height-prop['chart.gutter.bottom'])||mouseX<prop['chart.gutter.left']||mouseX>(ca.width-prop['chart.gutter.right'])){return null;}
if(prop['chart.xaxispos']=='center'){var value=(((this.grapharea/2)-(mouseY-prop['chart.gutter.top']))/this.grapharea)*(this.scale2.max-this.scale2.min)
value*=2;if(value>=0){value+=this.scale2.min;}else{value-=this.scale2.min;}}else if(prop['chart.xaxispos']=='top'){var value=((this.grapharea-(mouseY-prop['chart.gutter.top']))/this.grapharea)*(this.scale2.max-this.scale2.min)
value=this.scale2.max-value;value=Math.abs(value)*-1;}else{var value=((this.grapharea-(mouseY-prop['chart.gutter.top']))/this.grapharea)*(this.scale2.max-this.scale2.min)
value+=this.scale2.min;}
return value;}
RGraph.Bar.prototype.getYCoord=function(value)
{if(value>this.scale2.max){return null;}
var co=this.context;var ca=this.canvas;var prop=this.properties;var y;var xaxispos=prop['chart.xaxispos'];if(xaxispos=='top'){if(value<0){value=Math.abs(value);}
y=((value-this.scale2.min)/(this.scale2.max-this.scale2.min))*this.grapharea;y=y+this.gutterTop}else if(xaxispos=='center'){y=((value-this.scale2.min)/(this.scale2.max-this.scale2.min))*(this.grapharea/2);y=(this.grapharea/2)-y;y+=this.gutterTop;}else{if(value<this.scale2.min){value=this.scale2.min;}
y=((value-this.scale2.min)/(this.scale2.max-this.scale2.min))*this.grapharea;y=ca.height-this.gutterBottom-y;}
return y;}
RGraph.Bar.prototype.Highlight=function(shape)
{RGraph.Highlight.Rect(this,shape);}
RGraph.Bar.prototype.getObjectByXY=function(e)
{var ca=this.canvas;var prop=this.properties;var mouseXY=RGraph.getMouseXY(e);if(mouseXY[0]>=prop['chart.gutter.left']&&mouseXY[0]<=(ca.width-prop['chart.gutter.right'])&&mouseXY[1]>=prop['chart.gutter.top']&&mouseXY[1]<=(ca.height-prop['chart.gutter.bottom'])){return this;}}
RGraph.Bar.prototype.Adjusting_mousemove=function(e)
{var prop=this.properties;if(RGraph.Registry.Get('chart.adjusting')&&RGraph.Registry.Get('chart.adjusting').uid==this.uid){var value=Number(this.getValue(e));var shape=this.getShapeByX(e);if(shape){RGraph.Registry.Set('chart.adjusting.shape',shape);if(this.stackedOrGrouped&&prop['chart.grouping']=='grouped'){var indexes=RGraph.sequentialIndexToGrouped(shape['index'],this.data);this.data[indexes[0]][indexes[1]]=Number(value);}else{this.data[shape['index']]=Number(value);}
RGraph.RedrawCanvas(e.target);RGraph.FireCustomEvent(this,'onadjust');}}}
RGraph.CombinedChart=function()
{this.objects=[];var objects=arguments;if(RGraph.is_array(arguments[0])){objects=arguments[0];}
for(var i=0;i<objects.length;++i){this.objects[i]=objects[i];this.objects[i].Set('chart.gutter.left',this.objects[0].Get('chart.gutter.left'));this.objects[i].Set('chart.gutter.right',this.objects[0].Get('chart.gutter.right'));this.objects[i].Set('chart.gutter.top',this.objects[0].Get('chart.gutter.top'));this.objects[i].Set('chart.gutter.bottom',this.objects[0].Get('chart.gutter.bottom'));if(this.objects[i].type=='line'){this.objects[i].Set('chart.hmargin',((this.objects[0].canvas.width-this.objects[0].Get('chart.gutter.right')-this.objects[0].Get('chart.gutter.left'))/this.objects[0].data.length)/2);this.objects[i].Set('chart.noaxes',true);this.objects[i].Set('chart.background.grid',false);this.objects[i].Set('chart.ylabels',false);}
if(this.objects[i].Get('chart.resizable')){var resizable_object=this.objects[i];}}
if(resizable_object){function myOnresizebeforedraw(obj)
{var gutterLeft=obj.Get('chart.gutter.left');var gutterRight=obj.Get('chart.gutter.right');obj.Set('chart.hmargin',(obj.canvas.width-gutterLeft-gutterRight)/(obj.original_data[0].length*2));}
RGraph.AddCustomEventListener(resizable_object,'onresizebeforedraw',myOnresizebeforedraw);}}
RGraph.CombinedChart.prototype.Add=function(obj)
{this.objects.push(obj);}
RGraph.CombinedChart.prototype.Draw=function()
{for(var i=0;i<this.objects.length;++i){if(typeof(arguments[i])=='function'){arguments[i](this.objects[i]);}else{this.objects[i].Draw();}}}
RGraph.Bar.prototype.positionTooltip=function(obj,x,y,tooltip,idx)
{var prop=obj.properties;var coordX=obj.coords[tooltip.__index__][0];var coordY=obj.coords[tooltip.__index__][1];var coordW=obj.coords[tooltip.__index__][2];var coordH=obj.coords[tooltip.__index__][3];var canvasXY=RGraph.getCanvasXY(obj.canvas);var gutterLeft=prop['chart.gutter.left'];var gutterTop=prop['chart.gutter.top'];var width=tooltip.offsetWidth;var height=tooltip.offsetHeight;var value=obj.data_arr[tooltip.__index__];tooltip.style.left=0;tooltip.style.top=canvasXY[1]+coordY-height-7+'px';if(value<0){tooltip.style.top=canvasXY[1]+coordY+coordH+7+'px';}
tooltip.style.overflow='';var img=new Image();img.style.position='absolute';img.id='__rgraph_tooltip_pointer__';if(value>=0){img.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABEAAAAFCAYAAACjKgd3AAAARUlEQVQYV2NkQAN79+797+RkhC4M5+/bd47B2dmZEVkBCgcmgcsgbAaA9GA1BCSBbhAuA/AagmwQPgMIGgIzCD0M0AMMAEFVIAa6UQgcAAAAAElFTkSuQmCC';img.style.top=(tooltip.offsetHeight-2)+'px';}else{img.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAsAAAAFCAMAAACkeOZkAAAAK3RFWHRDcmVhdGlvbiBUaW1lAFNhdCA2IE9jdCAyMDEyIDEyOjQ5OjMyIC0wMDAw2S1RlgAAAAd0SU1FB9wKBgszM4Ed2k4AAAAJcEhZcwAACxIAAAsSAdLdfvwAAAAEZ0FNQQAAsY8L/GEFAAAACVBMVEX/AAC9vb3//+92Pom0AAAAAXRSTlMAQObYZgAAAB1JREFUeNpjYAABRgY4YGRiRDCZYBwQE8qBMEEcAANCACqByy1sAAAAAElFTkSuQmCC';img.style.top='-5px';}
tooltip.appendChild(img);if((canvasXY[0]+coordX+(coordW/2)-(width/2))<10){tooltip.style.left=(canvasXY[0]+coordX-(width*0.1))+(coordW/2)+'px';img.style.left=((width*0.1)-8.5)+'px';}else if((canvasXY[0]+coordX+(width/2))>document.body.offsetWidth){tooltip.style.left=canvasXY[0]+coordX-(width*0.9)+(coordW/2)+'px';img.style.left=((width*0.9)-8.5)+'px';}else{tooltip.style.left=(canvasXY[0]+coordX+(coordW/2)-(width*0.5))+'px';img.style.left=((width*0.5)-8.5)+'px';}}
RGraph.Bar.prototype.parseColors=function()
{var prop=this.properties;var colors=prop['chart.colors'];if(colors){for(var i=0;i<colors.length;++i){colors[i]=this.parseSingleColorForGradient(colors[i]);}}
var colors=prop['chart.key.colors'];if(colors){for(var i=0;i<colors.length;++i){colors[i]=this.parseSingleColorForGradient(colors[i]);}}
prop['chart.crosshairs.color']=this.parseSingleColorForGradient(prop['chart.crosshairs.color']);prop['chart.highlight.stroke']=this.parseSingleColorForGradient(prop['chart.highlight.stroke']);prop['chart.highlight.fill']=this.parseSingleColorForGradient(prop['chart.highlight.fill']);prop['chart.text.color']=this.parseSingleColorForGradient(prop['chart.text.color']);prop['chart.background.barcolor1']=this.parseSingleColorForGradient(prop['chart.background.barcolor1']);prop['chart.background.barcolor2']=this.parseSingleColorForGradient(prop['chart.background.barcolor2']);prop['chart.background.grid.color']=this.parseSingleColorForGradient(prop['chart.background.grid.color']);prop['chart.strokecolor']=this.parseSingleColorForGradient(prop['chart.strokecolor']);prop['chart.axis.color']=this.parseSingleColorForGradient(prop['chart.axis.color']);}
RGraph.Bar.prototype.parseSingleColorForGradient=function(color)
{if(!color||typeof(color)!='string'){return color;}
if(color.match(/^gradient\((.*)\)$/i)){var parts=RegExp.$1.split(':');var grad=this.context.createLinearGradient(0,this.canvas.height-this.properties['chart.gutter.bottom'],0,this.properties['chart.gutter.top']);var diff=1/(parts.length-1);grad.addColorStop(0,RGraph.trim(parts[0]));for(var j=1;j<parts.length;++j){grad.addColorStop(j*diff,RGraph.trim(parts[j]));}}
return grad?grad:color;}
RGraph.Bar.prototype.DrawBevel=function()
{var coords=this.coords;var coords2=this.coords2;var prop=this.properties;var co=this.context;var ca=this.canvas;if(prop['chart.grouping']=='stacked'){for(var i=0;i<coords2.length;++i){if(coords2[i]&&coords2[i][0]&&coords2[i][0][0]){var x=coords2[i][0][0];var y=coords2[i][0][1];var w=coords2[i][0][2];var arr=[];for(var j=0;j<coords2[i].length;++j){arr.push(coords2[i][j][3]);}
var h=RGraph.array_sum(arr);co.save();co.strokeStyle='black';co.beginPath();co.rect(x,y,w,h);co.clip();co.shadowColor='black';co.shadowOffsetX=0;co.shadowOffsetY=0;co.shadowBlur=20;co.beginPath();co.rect(x-3,y-3,w+6,h+100);co.lineWidth=5;co.stroke();co.restore();}}}else{for(var i=0;i<coords.length;++i){if(coords[i]){var x=coords[i][0];var y=coords[i][1];var w=coords[i][2];var h=coords[i][3];var xaxispos=prop['chart.xaxispos'];var xaxis_ycoord=((ca.height-this.gutterTop-this.gutterBottom)/2)+this.gutterTop;co.save();co.strokeStyle='black';co.beginPath();co.rect(x,y,w,h);co.clip();co.shadowColor='black';co.shadowOffsetX=0;co.shadowOffsetY=0;co.shadowBlur=20;if(xaxispos=='top'||(xaxispos=='center'&&(y+h)>xaxis_ycoord)){y=y-100;h=h+100;}else{y=y;h=h+100;}
co.beginPath();co.rect(x-3,y-3,w+6,h+6);co.lineWidth=5;co.stroke();co.restore();}}}}
