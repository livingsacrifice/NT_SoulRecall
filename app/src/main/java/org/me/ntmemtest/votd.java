package org.me.ntmemtest;

import java.util.Random;

public class votd {
	public static void shuffleArray(String[][] a, boolean inclOT) {
		int n = 365;
		if (inclOT)
			n = a[0].length;
	    Random random = new Random();
	    random.nextInt();
	    for (int i = 0; i < n; i++) {
	      int change = i + random.nextInt(n - i);
	      swap(a, i, change);
	    }
	  }

	  private static void swap(String[][] a, int i, int change) {
	    String s1 = a[0][i];
	    String s2 = a[1][i];
	    String s3 = a[2][i];
	    a[0][i] = a[0][change];
	    a[1][i] = a[1][change];
	    a[2][i] = a[2][change];
	    a[0][change] = s1;
	    a[1][change] = s2;
	    a[2][change] = s3;
	  }
	public static String[][] VerseOfTheDay() {
		String[][] chap = new String[3][465];
		//nt
		chap[0][0] = "John";
		chap[1][0] = "1";
		chap[2][0] = "1";
		chap[0][1] = "John";
		chap[1][1] = "3";
		chap[2][1] = "16";
		chap[0][2] = "John";
		chap[1][2] = "3";
		chap[2][2] = "17";
		chap[0][3] = "Romans";
		chap[1][3] = "3";
		chap[2][3] = "23";
		chap[0][4] = "Romans";
		chap[1][4] = "6";
		chap[2][4] = "23";
		chap[0][5] = "Revelation";
		chap[1][5] = "3";
		chap[2][5] = "20";
		chap[0][6] = "John";
		chap[1][6] = "14";
		chap[2][6] = "6";
		chap[0][7] = "Ephesians";
		chap[1][7] = "2";
		chap[2][7] = "8";
		chap[0][8] = "Ephesians";
		chap[1][8] = "2";
		chap[2][8] = "9";
		chap[0][9] = "2 Corinthians";
		chap[1][9] = "5";
		chap[2][9] = "17";
		chap[0][10] = "Romans";
		chap[1][10] = "8";
		chap[2][10] = "28";
		chap[0][11] = "Romans";
		chap[1][11] = "8";
		chap[2][11] = "38";
		chap[0][12] = "Romans";
		chap[1][12] = "8";
		chap[2][12] = "39";
		chap[0][13] = "Matthew";
		chap[1][13] = "11";
		chap[2][13] = "28";
		chap[0][14] = "Matthew";
		chap[1][14] = "11";
		chap[2][14] = "29";
		chap[0][15] = "Matthew";
		chap[1][15] = "11";
		chap[2][15] = "30";
		chap[0][16] = "Hebrews";
		chap[1][16] = "13";
		chap[2][16] = "8";
		chap[0][17] = "2 Peter";
		chap[1][17] = "3";
		chap[2][17] = "9";
		chap[0][18] = "2 Corinthians";
		chap[1][18] = "12";
		chap[2][18] = "9";
		chap[0][19] = "2 Corinthians";
		chap[1][19] = "4";
		chap[2][19] = "18";
		chap[0][20] = "Philippians";
		chap[1][20] = "4";
		chap[2][20] = "13";
		chap[0][21] = "Galatians";
		chap[1][21] = "2";
		chap[2][21] = "20";
		chap[0][22] = "James";
		chap[1][22] = "1";
		chap[2][22] = "22";
		chap[0][23] = "Colossians";
		chap[1][23] = "3";
		chap[2][23] = "23";
		chap[0][24] = "1 Corinthians";
		chap[1][24] = "15";
		chap[2][24] = "58";
		chap[0][25] = "James";
		chap[1][25] = "4";
		chap[2][25] = "7";
		chap[0][26] = "Luke";
		chap[1][26] = "16";
		chap[2][26] = "13";
		chap[0][27] = "1 John";
		chap[1][27] = "4";
		chap[2][27] = "7";
		chap[0][28] = "1 John";
		chap[1][28] = "4";
		chap[2][28] = "8";
		chap[0][29] = "Galatians";
		chap[1][29] = "5";
		chap[2][29] = "22";
		chap[0][30] = "Galatians";
		chap[1][30] = "5";
		chap[2][30] = "23";
		chap[0][31] = "Hebrews";
		chap[1][31] = "12";
		chap[2][31] = "1";
		chap[0][31] = "Hebrews";
		chap[1][31] = "12";
		chap[2][31] = "2";
		chap[0][32] = "Acts";
		chap[1][32] = "1";
		chap[2][32] = "8";
		chap[0][33] = "Romans";
		chap[1][33] = "12";
		chap[2][33] = "1";
		chap[0][34] = "Romans";
		chap[1][34] = "12";
		chap[2][34] = "2";
		chap[0][35] = "1 Thessalonians";
		chap[1][35] = "5";
		chap[2][35] = "18";
		chap[0][36] = "Philippians";
		chap[1][36] = "4";
		chap[2][36] = "6";
		chap[0][37] = "Philippians";
		chap[1][37] = "4";
		chap[2][37] = "7";
		chap[0][38] = "2 Timothy";
		chap[1][38] = "3";
		chap[2][38] = "16";
		chap[0][39] = "Hebrews";
		chap[1][39] = "4";
		chap[2][39] = "16";
		chap[0][40] = "1 John";
		chap[1][40] = "1";
		chap[2][40] = "9";
		chap[0][41] = "James";
		chap[1][41] = "5";
		chap[2][41] = "16";
		chap[0][42] = "1 Corinthians";
		chap[1][42] = "10";
		chap[2][42] = "13";
		chap[0][43] = "Matthew";
		chap[1][43] = "25";
		chap[2][43] = "40";
		chap[0][44] = "Matthew";
		chap[1][44] = "28";
		chap[2][44] = "19";
		chap[0][45] = "Matthew";
		chap[1][45] = "28";
		chap[2][45] = "20";
		chap[0][46] = "Matthew";
		chap[1][46] = "5";
		chap[2][46] = "16";
		chap[0][47] = "Ephesians";
		chap[1][47] = "6";
		chap[2][47] = "12";
		chap[0][48] = "Romans";
		chap[1][48] = "3";
		chap[2][48] = "24";
		chap[0][49] = "Romans";
		chap[1][49] = "5";
		chap[2][49] = "8";
		chap[0][50] = "Philippians";
		chap[1][50] = "4";
		chap[2][50] = "8";
		chap[0][51] = "1 Peter";
		chap[1][51] = "3";
		chap[2][51] = "15";
		chap[0][52] = "James";
		chap[1][52] = "1";
		chap[2][52] = "2";
		chap[0][53] = "2 Timothy";
		chap[1][53] = "3";
		chap[2][53] = "17";
		chap[0][54] = "James";
		chap[1][54] = "1";
		chap[2][54] = "3";
		chap[0][55] = "James";
		chap[1][55] = "1";
		chap[2][55] = "4";
		chap[0][56] = "James";
		chap[1][56] = "1";
		chap[2][56] = "5";
		chap[0][57] = "John";
		chap[1][57] = "1";
		chap[2][57] = "2";
		chap[0][58] = "Matthew";
		chap[1][58] = "5";
		chap[2][58] = "3";
		chap[0][59] = "Matthew";
		chap[1][59] = "5";
		chap[2][59] = "4";
		chap[0][60] = "Matthew";
		chap[1][60] = "5";
		chap[2][60] = "5";
		chap[0][61] = "Matthew";
		chap[1][61] = "5";
		chap[2][61] = "6";
		chap[0][62] = "Matthew";
		chap[1][62] = "5";
		chap[2][62] = "7";
		chap[0][63] = "Matthew";
		chap[1][63] = "5";
		chap[2][63] = "8";
		chap[0][64] = "Ephesians";
		chap[1][64] = "2";
		chap[2][64] = "10";
		chap[0][65] = "Romans";
		chap[1][65] = "10";
		chap[2][65] = "9";
		chap[0][66] = "John";
		chap[1][66] = "15";
		chap[2][66] = "7";
		chap[0][67] = "Galatians";
		chap[1][67] = "5";
		chap[2][67] = "16";
		chap[0][68] = "Matthew";
		chap[1][68] = "5";
		chap[2][68] = "9";
		chap[0][69] = "2 Timothy";
		chap[1][69] = "1";
		chap[2][69] = "7";
		chap[0][70] = "John";
		chap[1][70] = "1";
		chap[2][70] = "3";
		chap[0][71] = "Matthew";
		chap[1][71] = "5";
		chap[2][71] = "10";
		chap[0][72] = "Ephesians";
		chap[1][72] = "6";
		chap[2][72] = "11";
		chap[0][73] = "Philippians";
		chap[1][73] = "3";
		chap[2][73] = "8";
		chap[0][74] = "Ephesians";
		chap[1][74] = "6";
		chap[2][74] = "10";
		chap[0][75] = "Philippians";
		chap[1][75] = "3";
		chap[2][75] = "7";
		chap[0][76] = "John";
		chap[1][76] = "1";
		chap[2][76] = "12";
		chap[0][77] = "Matthew";
		chap[1][77] = "5";
		chap[2][77] = "11";
		chap[0][78] = "Titus";
		chap[1][78] = "1";
		chap[2][78] = "2";
		chap[0][79] = "Titus";
		chap[1][79] = "1";
		chap[2][79] = "1";
		chap[0][80] = "Ephesians";
		chap[1][80] = "6";
		chap[2][80] = "13";
		chap[0][81] = "Titus";
		chap[1][81] = "1";
		chap[2][81] = "3";
		chap[0][82] = "Ephesians";
		chap[1][82] = "6";
		chap[2][82] = "14";
		chap[0][83] = "Ephesians";
		chap[1][83] = "6";
		chap[2][83] = "15";
		chap[0][84] = "Ephesians";
		chap[1][84] = "6";
		chap[2][84] = "16";
		chap[0][85] = "John";
		chap[1][85] = "1";
		chap[2][85] = "14";
		chap[0][86] = "Matthew";
		chap[1][86] = "4";
		chap[2][86] = "4";
		chap[0][87] = "Hebrews";
		chap[1][87] = "4";
		chap[2][87] = "12";
		chap[0][88] = "John";
		chap[1][88] = "16";
		chap[2][88] = "24";
		chap[0][89] = "Matthew";
		chap[1][89] = "21";
		chap[2][89] = "22";
		chap[0][90] = "John";
		chap[1][90] = "14";
		chap[2][90] = "21";
		chap[0][91] = "Hebrews";
		chap[1][91] = "5";
		chap[2][91] = "8";
		chap[0][92] = "Matthew";
		chap[1][92] = "4";
		chap[2][92] = "19";
		chap[0][93] = "Romans";
		chap[1][93] = "1";
		chap[2][93] = "16";
		chap[0][94] = "Hebrews";
		chap[1][94] = "9";
		chap[2][94] = "27";
		chap[0][95] = "John";
		chap[1][95] = "5";
		chap[2][95] = "24";
		chap[0][96] = "1 John";
		chap[1][96] = "5";
		chap[2][96] = "13";
		chap[0][97] = "Romans";
		chap[1][97] = "8";
		chap[2][97] = "37";
		chap[0][98] = "John";
		chap[1][98] = "3";
		chap[2][98] = "30";
		chap[0][99] = "John";
		chap[1][99] = "13";
		chap[2][99] = "13";
		chap[0][100] = "John";
		chap[1][100] = "13";
		chap[2][100] = "14";
		chap[0][101] = "2 Corinthians";
		chap[1][101] = "5";
		chap[2][101] = "15";
		chap[0][102] = "1 Corinthians";
		chap[1][102] = "3";
		chap[2][102] = "16";
		chap[0][103] = "1 Corinthians";
		chap[1][103] = "3";
		chap[2][103] = "17";
		chap[0][104] = "1 Corinthians";
		chap[1][104] = "6";
		chap[2][104] = "19";
		chap[0][105] = "1 Corinthians";
		chap[1][105] = "6";
		chap[2][105] = "20";
		chap[0][106] = "John";
		chap[1][106] = "14";
		chap[2][106] = "26";
		chap[0][107] = "John";
		chap[1][107] = "4";
		chap[2][107] = "23";
		chap[0][108] = "Hebrews";
		chap[1][108] = "13";
		chap[2][108] = "5";
		chap[0][109] = "John";
		chap[1][109] = "14";
		chap[2][109] = "2";
		chap[0][110] = "John";
		chap[1][110] = "14";
		chap[2][110] = "3";
		chap[0][111] = "Romans";
		chap[1][111] = "8";
		chap[2][111] = "32";
		chap[0][112] = "Philippians";
		chap[1][112] = "4";
		chap[2][112] = "19";
		chap[0][113] = "Luke";
		chap[1][113] = "6";
		chap[2][113] = "38";
		chap[0][114] = "Hebrews";
		chap[1][114] = "1";
		chap[2][114] = "9";
		chap[0][115] = "2 Thessalonians";
		chap[1][115] = "3";
		chap[2][115] = "3";
		chap[0][116] = "1 Corinthians";
		chap[1][116] = "1";
		chap[2][116] = "9";
		chap[0][117] = "2 Corinthians";
		chap[1][117] = "9";
		chap[2][117] = "8";
		chap[0][118] = "2 Peter";
		chap[1][118] = "1";
		chap[2][118] = "2";
		chap[0][119] = "2 Peter";
		chap[1][119] = "1";
		chap[2][119] = "3";
		chap[0][120] = "Hebrews";
		chap[1][120] = "4";
		chap[2][120] = "15";
		chap[0][121] = "Colossians";
		chap[1][121] = "3";
		chap[2][121] = "24";
		chap[0][122] = "Philippians";
		chap[1][122] = "2";
		chap[2][122] = "3";
		chap[0][123] = "Philippians";
		chap[1][123] = "2";
		chap[2][123] = "4";
		chap[0][124] = "2 Peter";
		chap[1][124] = "3";
		chap[2][124] = "14";
		chap[0][125] = "Revelation";
		chap[1][125] = "20";
		chap[2][125] = "15";
		chap[0][126] = "Ephesians";
		chap[1][126] = "4";
		chap[2][126] = "29";
		chap[0][127] = "James";
		chap[1][127] = "1";
		chap[2][127] = "19";
		chap[0][128] = "James";
		chap[1][128] = "1";
		chap[2][128] = "20";
		chap[0][129] = "Hebrews";
		chap[1][129] = "11";
		chap[2][129] = "6";
		chap[0][130] = "Romans";
		chap[1][130] = "4";
		chap[2][130] = "17";
		chap[0][131] = "Romans";
		chap[1][131] = "4";
		chap[2][131] = "20";
		chap[0][132] = "Romans";
		chap[1][132] = "4";
		chap[2][132] = "21";
		chap[0][133] = "Hebrews";
		chap[1][133] = "10";
		chap[2][133] = "38";
		chap[0][134] = "Luke";
		chap[1][134] = "9";
		chap[2][134] = "23";
		chap[0][135] = "John";
		chap[1][135] = "8";
		chap[2][135] = "31";
		chap[0][136] = "Colossians";
		chap[1][136] = "2";
		chap[2][136] = "6";
		chap[0][137] = "Colossians";
		chap[1][137] = "2";
		chap[2][137] = "7";
		chap[0][138] = "Galatians";
		chap[1][138] = "6";
		chap[2][138] = "7";
		//ot
		chap[0][365] = "Genesis";
		chap[1][365] = "1";
		chap[2][365] = "1";
		chap[0][366] = "Isaiah";
		chap[1][366] = "9";
		chap[2][366] = "6";
		chap[0][367] = "Isaiah";
		chap[1][367] = "40";
		chap[2][367] = "28";
		chap[0][368] = "Isaiah";
		chap[1][368] = "40";
		chap[2][368] = "30";
		chap[0][369] = "Isaiah";
		chap[1][369] = "40";
		chap[2][369] = "31";
		chap[0][370] = "Psalm";
		chap[1][370] = "27";
		chap[2][370] = "1";
		chap[0][371] = "Jeremiah";
		chap[1][371] = "29";
		chap[2][371] = "11";
		chap[0][372] = "Lamentations";
		chap[1][372] = "3";
		chap[2][372] = "22";
		chap[0][373] = "Lamentations";
		chap[1][373] = "3";
		chap[2][373] = "23";
		chap[0][374] = "Psalm";
		chap[1][374] = "37";
		chap[2][374] = "4";
		chap[0][375] = "Psalm";
		chap[1][375] = "37";
		chap[2][375] = "5";
		chap[0][376] = "Psalm";
		chap[1][376] = "19";
		chap[2][376] = "14";
		chap[0][377] = "Psalm";
		chap[1][377] = "119";
		chap[2][377] = "105";
		chap[0][378] = "Psalm";
		chap[1][378] = "119";
		chap[2][378] = "11";
		chap[0][379] = "Micah";
		chap[1][379] = "6";
		chap[2][379] = "8";
		chap[0][380] = "Joshua";
		chap[1][380] = "1";
		chap[2][380] = "9";
		chap[0][381] = "Isaiah";
		chap[1][381] = "53";
		chap[2][381] = "5";
		chap[0][382] = "Proverbs";
		chap[1][382] = "3";
		chap[2][382] = "5";
		chap[0][383] = "Proverbs";
		chap[1][383] = "3";
		chap[2][383] = "6";
		chap[0][384] = "Psalm";
		chap[1][384] = "1";
		chap[2][384] = "1";
		chap[0][385] = "Psalm";
		chap[1][385] = "1";
		chap[2][385] = "2";
		chap[0][386] = "Psalm";
		chap[1][386] = "1";
		chap[2][386] = "3";
		chap[0][387] = "Psalm";
		chap[1][387] = "23";
		chap[2][387] = "1";
		chap[0][388] = "Psalm";
		chap[1][388] = "23";
		chap[2][388] = "2";
		chap[0][389] = "Psalm";
		chap[1][389] = "23";
		chap[2][389] = "3";
		chap[0][390] = "Psalm";
		chap[1][390] = "23";
		chap[2][390] = "4";
		chap[0][391] = "Psalm";
		chap[1][391] = "23";
		chap[2][391] = "5";
		chap[0][392] = "Psalm";
		chap[1][392] = "23";
		chap[2][392] = "6";
		chap[0][392] = "Psalm";
		chap[1][392] = "1";
		chap[2][392] = "4";
		chap[0][393] = "Psalm";
		chap[1][393] = "1";
		chap[2][393] = "5";
		chap[0][394] = "Psalm";
		chap[1][394] = "1";
		chap[2][394] = "6";
		chap[0][395] = "Joshua";
		chap[1][395] = "1";
		chap[2][395] = "8";
		chap[0][396] = "Deuteronomy";
		chap[1][396] = "6";
		chap[2][396] = "5";
		chap[0][397] = "Exodus";
		chap[1][397] = "14";
		chap[2][397] = "14";
		chap[0][398] = "Genesis";
		chap[1][398] = "1";
		chap[2][398] = "2";
		chap[0][399] = "Exodus";
		chap[1][399] = "20";
		chap[2][399] = "3";
		chap[0][400] = "Exodus";
		chap[1][400] = "20";
		chap[2][400] = "12";
		chap[0][401] = "Deuteronomy";
		chap[1][401] = "6";
		chap[2][401] = "4";
		chap[0][402] = "Deuteronomy";
		chap[1][402] = "6";
		chap[2][402] = "7";
		chap[0][403] = "Genesis";
		chap[1][403] = "2";
		chap[2][403] = "1";
		chap[0][404] = "Exodus";
		chap[1][404] = "20";
		chap[2][404] = "8";
		chap[0][405] = "Psalm";
		chap[1][405] = "100";
		chap[2][405] = "2";
		chap[0][406] = "Deuteronomy";
		chap[1][406] = "6";
		chap[2][406] = "6";
		chap[0][407] = "Exodus";
		chap[1][407] = "20";
		chap[2][407] = "13";
		chap[0][408] = "Exodus";
		chap[1][408] = "20";
		chap[2][408] = "14";
		chap[0][409] = "Exodus";
		chap[1][409] = "20";
		chap[2][409] = "15";
		chap[0][410] = "Jeremiah";
		chap[1][410] = "15";
		chap[2][410] = "16";
		chap[0][411] = "Proverbs";
		chap[1][411] = "24";
		chap[2][411] = "11";
		chap[0][412] = "Proverbs";
		chap[1][412] = "24";
		chap[2][412] = "12";
		chap[0][413] = "Isaiah";
		chap[1][413] = "30";
		chap[2][413] = "18";
		chap[0][414] = "Psalm";
		chap[1][414] = "103";
		chap[2][414] = "13";
		chap[0][415] = "Psalm";
		chap[1][415] = "103";
		chap[2][415] = "14";
		chap[0][416] = "Jeremiah";
		chap[1][416] = "31";
		chap[2][416] = "3";
		chap[0][417] = "Isaiah";
		chap[1][417] = "49";
		chap[2][417] = "15";
		chap[0][418] = "Isaiah";
		chap[1][418] = "49";
		chap[2][418] = "16";
		chap[0][419] = "Isaiah";
		chap[1][419] = "26";
		chap[2][419] = "3";
		chap[0][420] = "Psalm";
		chap[1][420] = "147";
		chap[2][420] = "3";
		chap[0][421] = "Psalm";
		chap[1][421] = "147";
		chap[2][421] = "4";
		chap[0][422] = "Psalm";
		chap[1][422] = "147";
		chap[2][422] = "5";
		chap[0][423] = "Psalm";
		chap[1][423] = "62";
		chap[2][423] = "11";
		chap[0][424] = "1 Chronicles";
		chap[1][424] = "29";
		chap[2][424] = "11";
		chap[0][425] = "1 Chronicles";
		chap[1][425] = "29";
		chap[2][425] = "12";
		chap[0][426] = "Psalm";
		chap[1][426] = "18";
		chap[2][426] = "2";
		chap[0][427] = "Proverbs";
		chap[1][427] = "13";
		chap[2][427] = "10";
		chap[0][428] = "Psalm";
		chap[1][428] = "46";
		chap[2][428] = "1";
		chap[0][429] = "Psalm";
		chap[1][429] = "46";
		chap[2][429] = "10";
		chap[0][430] = "Psalm";
		chap[1][430] = "18";
		chap[2][430] = "1";
		chap[0][431] = "Isaiah";
		chap[1][431] = "63";
		chap[2][431] = "9";
		chap[0][432] = "Psalm";
		chap[1][432] = "61";
		chap[2][432] = "2";
		chap[0][433] = "2 Chronicles";
		chap[1][433] = "14";
		chap[2][433] = "11";
		chap[0][434] = "Psalm";
		chap[1][434] = "84";
		chap[2][434] = "11";
		chap[0][435] = "Psalm";
		chap[1][435] = "62";
		chap[2][435] = "5";
		chap[0][436] = "Psalm";
		chap[1][436] = "27";
		chap[2][436] = "14";
		chap[0][437] = "Proverbs";
		chap[1][437] = "17";
		chap[2][437] = "27";
		chap[0][438] = "Proverbs";
		chap[1][438] = "17";
		chap[2][438] = "28";
		chap[0][439] = "Isaiah";
		chap[1][439] = "32";
		chap[2][439] = "17";
		chap[0][440] = "Psalm";
		chap[1][440] = "107";
		chap[2][440] = "9";
		chap[0][441] = "Psalm";
		chap[1][441] = "27";
		chap[2][441] = "4";
		chap[0][442] = "Psalm";
		chap[1][442] = "27";
		chap[2][442] = "8";
		chap[0][443] = "Isaiah";
		chap[1][443] = "43";
		chap[2][443] = "4";
		chap[0][444] = "Deuteronomy";
		chap[1][444] = "1";
		chap[2][444] = "11";
		chap[0][445] = "Genesis";
		chap[1][445] = "22";
		chap[2][445] = "18";
		chap[0][446] = "Isaiah";
		chap[1][446] = "59";
		chap[2][446] = "21";
		chap[0][447] = "Ecclesiastes";
		chap[1][447] = "10";
		chap[2][447] = "1";
		chap[0][448] = "Ecclesiastes";
		chap[1][448] = "8";
		chap[2][448] = "11";
		chap[0][449] = "1 Samuel";
		chap[1][449] = "15";
		chap[2][449] = "22";
		chap[0][450] = "1 Samuel";
		chap[1][450] = "15";
		chap[2][450] = "23";
		return chap;
	}
}
