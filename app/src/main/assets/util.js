//Вычислеие систематической ошибки (1)
var m = function (tracks, etalon){
  var res = 0; 
  for (var j = 0, i = 0; i < etalon.length; i++) {
    if(tracks[j]){
      if(etalon[i]['time']==tracks[j]['time']){
        res += tracks[j].distanceTo(etalon[i]);
        j++;
      }
    }
  };
  res /= tracks.length;
  return parseFloat(res.toFixed(2));
};
//(2)
var nu = function (tracks, etalon){
  var res = 0; 
  for (var i = 0, j = 0; i < etalon.length; i++) {
    if(tracks[j]){
      if(etalon[i]['time']==tracks[j]['time']){
        res += Math.pow(tracks[j].distanceTo(etalon[i]),2);
        j++;
      }  
    }
    
  };
  res /= tracks.length-1;
  res = Math.sqrt(res);
  return parseFloat(res.toFixed(2));
};
//(3)
var sig = function (tracks, etalon){
  var res = 0; 
  var sysErr = m(tracks, etalon);
  for (var j = 0, i = 0; i < etalon.length; i++) {
    if(tracks[j]){
      if(etalon[i]['time']==tracks[j]['time']){
        res += Math.pow(tracks[j].distanceTo(etalon[i]) - sysErr,2);
        j++;
      }  
    }
    
  };
  res /= tracks.length-1;
  res = Math.sqrt(res);
  return parseFloat(res.toFixed(2));
};
//Получение рандомного цвета
function get_random_color() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.round(Math.random() * 15)];
    }
    return color;
}

//кроссбраузерная анимации
if (!window.requestAnimationFrame) {
    window.requestAnimationFrame = (window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            window.msRequestAnimationFrame ||
            window.oRequestAnimationFrame ||
            function (callback) {
                return window.setTimeout(callback, 17 );
            });
}
var utils = {};

//получение координат курсора (с кроссбраузерной магией)
utils.captureMouse = function (element) {
    var mouse = {x: 0, y: 0};
    element.addEventListener('mousemove', function (event) {
        var x, y;
        if (event.pageX || event.pageY) {
            x = event.pageX;
            y = event.pageY;
        } else {
            x = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
            y = event.clientY + document.body.scrollTop + document.documentElement.scrollTop;
        }
        x -= element.offsetLeft;
        y -= element.offsetTop;
        mouse.x = x;
        mouse.y = y;
    }, false);
    return mouse;
};

//проверка на входждение точек x и y в квадратик rect
utils.containsPoint = function (rect, x, y) {
  return !(x < rect.x ||
           x > rect.x + rect.width ||
           y < rect.y ||
           y > rect.y + rect.height);
};

/**
 * Возвращает цвет в формате: '#RRGGBB', или в hex number.
 */
utils.parseColor = function (color, toNumber) {
  if (toNumber === true) {
    if (typeof color === 'number') {
      return (color | 0); //chop off decimal
    }
    if (typeof color === 'string' && color[0] === '#') {
      color = color.slice(1);
    }
    return window.parseInt(color, 16);
  } else {
    if (typeof color === 'number') {
      color = '#' + ('00000' + (color | 0).toString(16)).substr(-6); //pad
    }
    return color;
  }
};
