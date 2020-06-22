# 通用接口(C->S):
`getInform` 请求获取玩家人数和服务器信息&ensp;&ensp;"{}"&ensp;&ensp;√
<br>`register` 注册验证码发送请求 &ensp;&ensp;"{"type":"email","num":"xxx@qq.com"}"&ensp;&ensp;√
<br>`matchCAPTCHA` 验证码匹配请求 &ensp;&ensp;"{"CAPTCHA":"XXXXXX"}"&ensp;&ensp;√
<br>`initUser` 初始化账号 &ensp;&ensp;"{"user":"XXXXXX","password":"XXX","name":"张三"...}"&ensp;&ensp;√
<br>`login` 登录请求 &ensp;&ensp;"{"type":"userPass","num":"xxx","password":"xxxx"}"&ensp;&ensp;√
<br>`getPlayerInform` 请求玩家信息 &ensp;&ensp;"{"player":"hs666"}"&ensp;&ensp;√
<br>`createRoom` 创建房间 &ensp;&ensp;"{}"&ensp;&ensp;√
<br>`updateRoomInform` 请求房间信息 &ensp;&ensp;"{}"&ensp;&ensp;√
<br>`joinRoom` 加入房间 &ensp;&ensp;"{"roomID":10001}"&ensp;&ensp;√
<br>`joinRoomPws` 加入房间密码反馈 &ensp;&ensp;"{"pws":"password"}"&ensp;&ensp;√
<br>`ready` 准备！ &ensp;&ensp;"{}"&ensp;&ensp;√
<br>`updataChecker` 请求更新游戏数据 &ensp;&ensp;"{}"&ensp;&ensp;√
<br>`play` 下棋！！！ &ensp;&ensp;"{"play":[1,2,3]}"&ensp;&ensp;√



# 通用接口(S->C):
`getInform` 返回玩家人数和服务器信息&ensp;&ensp;"{"player":"0/20","inform":"XXX欢迎您"}"&ensp;&ensp;√
<br>`matchCAPTCHA` 验证码匹配结果反馈，成功则请求初始化账号 &ensp;&ensp;"{"result":true}"&ensp;&ensp;√
<br>`initUser` 初始化结果反馈 &ensp;&ensp;"{"result":false,"reason":"账号已被注册"}"&ensp;&ensp;√
<br>`login` 登录结果验证反馈 &ensp;&ensp;"{"result":false}"&ensp;&ensp;√
<br>`getPlayerInform` 返回玩家信息 &ensp;&ensp;"{"name":"hs","head" : "base64","sex":"man","gamenum":0,"winnum":0,"drawnum":0,"failnum":0}"&ensp;&ensp;√
<br>`updateRoomInform` 返回房间信息 &ensp;&ensp;"{"black":(json),"white":(json),"roomID":10001......}"√
<br>`joinRoom` 加入房间结果反馈 &ensp;&ensp;"{"result":false}"&ensp;&ensp;√
<br>`joinRoomPws` 加入房间密码结果反馈 &ensp;&ensp;"{"result":false}"&ensp;&ensp;√
<br>`updataChecker` 更新游戏数据 &ensp;&ensp;"{"board":{"notEmote":123,"isBlack":123,"isKing":123},"upChess":[0,50,40],"nowPro":[[0,10],[10,20]],"wait":true}"&ensp;&ensp;√
<br>`win` 胜利反馈！ &ensp;&ensp;"{"win":true}"&ensp;&ensp;√
