package com.main.virtualvisitor88;import android.content.Context;import android.content.res.Resources;import android.graphics.Bitmap;import android.graphics.BitmapFactory;import android.graphics.Point;import android.graphics.drawable.Drawable;public class Temples {		enum Temple{		//徳島1~23		Ryouzenzi(1,"霊山寺",new Point(0,0),0.0),		Gokurakuzi(2,"極楽寺",new Point(0,0),0.0),		Konsenzi(3,"金泉寺",new Point(0,0),0.0),		Dainitizi(4,"大日寺",new Point(0,0),0.0),		Zizouzi(5,"地蔵寺",new Point(0,0),0.0),		Anrakuzi(6,"安楽寺",new Point(0,0),0.0),		Zyurakuzi(7,"十楽寺",new Point(0,0),0.0),		Kumadanizi(8,"熊谷寺",new Point(0,0),0.0),		Hourinzi(9,"法輪寺",new Point(0,0),0.0),		Kirihatazi(10,"切幡寺",new Point(0,0),0.0),		Huziidera(11,"藤井寺",new Point(0,0),0.0),		Syousanzi(12,"焼山寺",new Point(0,0),0.0),		Dainichizi(13,"大日寺",new Point(0,0),0.0),		Zyourakuzi(14,"常楽寺",new Point(0,0),0.0),		Kokubunzi(15,"国分寺",new Point(0,0),0.0),		Kannonzi(16,"観音寺",new Point(0,0),0.0),		Idozi(17,"井戸寺",new Point(0,0),0.0),		Onzanzi(18,"恩山寺",new Point(0,0),0.0),		Tatuezi(19,"立江寺",new Point(0,0),0.0),		Kakurinzi(20,"鶴林寺",new Point(0,0),0.0),		Tairyuzi(21,"太龍寺",new Point(0,0),0.0),		Byoudouzi(22,"平等寺",new Point(0,0),0.0),		Yakuouzi(23,"薬王寺",new Point(0,0),0.0),		//高知県24~39		Hotsumisakizi(24,"最御崎寺",new Point(0,0),0.0),		Sinsyouzi(25,"津照寺",new Point(0,0),0.0),		Kongoutyouzi(26,"金剛頂寺",new Point(0,0),0.0),		Kounominezi(27,"神峯寺",new Point(0,0),0.0),		Dainitiji(28,"大日寺",new Point(0,0),0.0),		Kocubunzi(29,"国分寺",new Point(0,0),0.0),		Zenrakuzi(30,"善楽寺",new Point(0,0),0.0),		Chikurinzi(31,"竹林寺",new Point(0,0),0.0),		Zenzibuzi(32,"禅師峰寺",new Point(0,0),0.0),		Sekkeizi(33,"雪蹊寺",new Point(0,0),0.0),		Tanemazi(34,"種間寺",new Point(0,0),0.0),		Kiyotakizi(35,"清瀧寺",new Point(0,0),0.0),		Shouryuzi(36,"青龍寺",new Point(0,0),0.0),		Iwamotozi(37,"岩本寺",new Point(0,0),0.0),		Konzouhukuzi(38,"金剛福寺",new Point(0,0),0.0),		Enkouzi(39,"延光寺",new Point(0,0),0.0),		//愛知県40~65		Kanzaizi(40,"観自在",new Point(0,0),0.0),		Ryukouzi(41,"龍光寺",new Point(0,0),0.0),		Butumokuzi(42,"佛木寺",new Point(0,0),0.0),		Meisekizi(43,"明石寺",new Point(0,0),0.0),		Daihouzi(44,"大寶寺",new Point(0,0),0.0),		Iwayazi(45,"岩屋寺",new Point(0,0),0.0),		Zyoururizi(46,"浄瑠璃寺",new Point(0,0),0.0),		Yasakazi(47,"八坂寺",new Point(0,0),0.0),		Sairinzi(48,"西林寺",new Point(0,0),0.0),		Zyoudozi(49,"浄土寺",new Point(0,0),0.0),		Hantazi(50,"繁多寺",new Point(0,0),0.0),		Isitezi(51,"石手寺",new Point(0,0),0.0),		Taisanji(52,"太山寺",new Point(0,0),0.0),		Enmyouzi(53,"圓明寺",new Point(0,0),0.0),		Enmeizi(54,"延命寺",new Point(0,0),0.0),		Nankoubou(55,"南光坊",new Point(0,0),0.0),		Taisanzi(56,"泰山寺",new Point(0,0),0.0),		Eihukuzi(57,"栄福寺",new Point(0,0),0.0),		Senyuuzi(58,"仙遊寺",new Point(0,0),0.0),		Kokubunji(59,"国分寺",new Point(0,0),0.0),		Yokominezi(60,"横峰寺",new Point(0,0),0.0),		Kouonzi(61,"香園寺",new Point(0,0),0.0),		Hozyuzi(62,"宝寿寺",new Point(0,0),0.0),		Kitizyouzi(63,"吉祥寺",new Point(0,0),0.0),		Maegamizi(64,"前神寺",new Point(0,0),0.0),		Sankakuzi(65,"三角寺",new Point(0,0),0.0),		//香川県66~88		Unpenzi(66,"雲辺寺",new Point(0,0),0.0),		Daikouzi(67,"大興寺",new Point(0,0),0.0),		Zinnein(68,"神恵院",new Point(0,0),0.0),		Kanonzi(69,"観音寺",new Point(0,0),0.0),		Motoyamazi(70,"本山寺",new Point(0,0),0.0),		Iyadanizi(71,"弥谷寺",new Point(0,0),0.0),		Mandarazi(72,"曼荼羅寺",new Point(0,0),0.0),		Syussyakazi(73,"出釈迦寺",new Point(0,0),0.0),		Kouyamazi(74,"甲山寺",new Point(0,0),0.0),		Zentuuzi(75,"善通寺",new Point(0,0),0.0),		Konnzouzi(76,"金倉寺",new Point(0,0),0.0),		Douryouzi(77,"道隆寺",new Point(0,0),0.0),		Gousyouzi(78,"郷照寺",new Point(0,0),0.0),		Tennouzi(79,"天皇寺",new Point(0,0),0.0),		Kokuvunzi(80,"國分寺",new Point(0,0),0.0),		Shiraminezi(81,"白峯寺",new Point(0,0),0.0),		Negorozi(82,"根香寺",new Point(0,0),0.0),		Itinomiyazi(83,"一宮寺",new Point(0,0),0.0),		Yashimazi(84,"屋島寺",new Point(0,0),0.0),		Yakurizi(85,"八栗寺",new Point(0,0),0.0),		Sidozi(86,"志度寺",new Point(0,0),0.0),		Nagaozi(87,"長尾寺",new Point(0,0),0.0),		Ookubozi(88,"大窪寺",new Point(0,0),0.0),		;		//お寺の番号		int id;		//お寺の名前		String name;		//お寺の場所		Point point;		//累積距離		Double distance;				Temple(int id,String name,Point point,Double distance){			this.id=id;			this.name=name;			this.point=point;			this.distance=distance;		}	}	//お寺番号1~88	static Temple getTemple(int id){		Temple[] data = Temple.values();		return data[(id-1)%88];	}	static Double getDistance(Temple temple1,Temple temple2){		Double dis = Math.pow((temple1.point.x-temple2.point.x),2)+Math.pow((temple1.point.y-temple2.point.y),2);		return Math.sqrt(dis);	}	/*	String getInfo(int id){			}*/			static Bitmap getImage(int id,Context context){		Resources res = context.getResources();		return BitmapFactory.decodeResource(res,R.drawable.temple);	}}