/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ntmemtest;

/**
 *
 * @author srichard
 */
public class VerseCompare {

    private String txtUser;
    private String txtVerse;
    private String wordScore;
    private String orderScore;
    private boolean preview;
    private static int previewLength;
    private static String fiveWordsText;

    public VerseCompare(String inText, String outText, boolean aPreview, int aPreviewLength){
        txtUser = inText;
        txtVerse = outText;
        preview = aPreview;
        previewLength = aPreviewLength;
    }

    public void checkVerse(){
        //Compare against input
        String sent1 = txtUser;
        String sent2 = txtVerse;
        String sentA = txtUser;
        String sentB = txtVerse;
        //remove non-alphanumeric characters
        String[] oldchars = {"(",")","'",",",":",".","?",";","!","\"","-","\r","\n","*","_"};
        for (int i = 0; i <= oldchars.length-1; i++){
            sent1 = sent1.replace(oldchars[i], "");
            sent2 = sent2.replace(oldchars[i], "");
            if (i >= 13){
                sentA = sentA.replace(oldchars[i],"");
            }
        }
        //second pass to remove quotes (single & double)
        char singQ = ('\'');
        char doubQ = ('\"');
        char openQ = (char)147;
        char closeQ = (char)148;
        for (int i = sent1.length() -1; i >= 0; i--){
            if ((sent1.charAt(i) == singQ) || (sent1.charAt(i) == doubQ)
                    || ((int)sent1.charAt(i) > 127)){
                if (i == sent1.length()-1){
                    sent1 = sent1.substring(0, i);
                } else{
                    sent1 = sent1.substring(0, i) + sent1.substring(i+1);
                }
            }
        }
        for (int i = sent2.length() -1; i >= 0; i--){
            if ((sent2.charAt(i) == singQ) || (sent2.charAt(i) == doubQ)
                    || ((int)sent2.charAt(i) > 127)){
                if (i == sent2.length()-1){
                    sent2 = sent2.substring(0, i);
                } else{
                    sent2 = sent2.substring(0, i) + sent2.substring(i+1);
                }
            }
        }
        sent1 = sent1.toLowerCase().trim();
        sent2 = sent2.toLowerCase().trim();
        sentA = sentA.trim();
        sentB = sentB.trim();
        //parse output
        String[][] words2 = null; //non-punctuated
        int j = 0;
        for (int i = 0; i <= sent2.length() - 2; i ++){
            if (sent2.substring(i,i+1).equals(" ") && !sent2.substring(i+1,i+2).equals(" ")){
                String[][] tempVar;
                if (words2 != null){
                    tempVar = new String[4][words2[0].length+1];
                    for (int Dimension0 = 0; Dimension0 < words2.length; Dimension0++){
                        int CopyLength = Math.min(words2[0].length, tempVar[0].length);
                        for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                            tempVar[Dimension0][Dimension1] = words2[Dimension0][Dimension1];
                        }
                    }
                }
                else{
                    tempVar = new String[4][1];
                }
                words2 = tempVar;
                words2[0][words2[0].length-1] = sent2.substring(j,i).trim();
                words2[1][words2[0].length-1] = Integer.toString(j);

                j = i + 1;

            }
        }

        String[][] tempVar2;
        if (words2 != null){
            tempVar2 = new String[4][words2[0].length + 1];
            for (int Dimension0 = 0; Dimension0 < words2.length; Dimension0++){
                int CopyLength = Math.min(words2[0].length, tempVar2[0].length);
                for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                    tempVar2[Dimension0][Dimension1] = words2[Dimension0][Dimension1];
                }
            }
        }
        else{
            tempVar2 = new String[4][1];
        }
        words2 = tempVar2;
        words2[0][words2[0].length-1] = sent2.substring(j,sent2.length()).trim();
        words2[1][words2[0].length-1] = Integer.toString(j);

        String[][] wordsB = null; //punctuated
        wordsB = new String[2][0];
        j = 0;
        for (int i = 0; i <= sentB.length() - 2; i ++){
            if (sentB.substring(i,i+1).equals(" ") && !sentB.substring(i+1,i+2).equals(" ")){
                String[][] tempVar3;
                if (wordsB != null){
                    tempVar3 = new String[2][wordsB[0].length +1];
                    for (int Dimension0 = 0; Dimension0 < wordsB.length; Dimension0++){
                        int CopyLength = Math.min(wordsB[0].length,tempVar3[0].length);
                        for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                            tempVar3[Dimension0][Dimension1] = wordsB[Dimension0][Dimension1];
                        }
                    }
                }
                else{
                    tempVar3 = new String[2][1];
                }
                wordsB = tempVar3;
                wordsB[0][wordsB[0].length-1] = sentB.substring(j,i).trim();
                wordsB[1][wordsB[0].length-1] = Integer.toString(j);
                j = i + 1;
            }
        }

        String[][] tempVar4;
        if (wordsB != null){
            tempVar4 = new String[2][wordsB[0].length+1];
            for (int Dimension0 = 0; Dimension0 < wordsB.length; Dimension0++){
                int CopyLength = Math.min(wordsB[0].length, tempVar4[0].length);
                for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                    tempVar4[Dimension0][Dimension1] = wordsB[Dimension0][Dimension1];
                }
            }
        }
        else{
            tempVar4 = new String[2][1];
        }
        wordsB = tempVar4;
        wordsB[0][wordsB[0].length-1] = sentB.substring(j, sentB.length()).trim();
        wordsB[1][wordsB[0].length-1] = Integer.toString(j);

        //preview output
        if (preview){
            String[][] tempVar5 = new String[4][previewLength];
            if (words2 != null){
                for (int Dim0 = 0; Dim0 < words2.length; Dim0++){
                    int CopyLength = Math.min(words2[0].length, tempVar5[0].length);
                    for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                        tempVar5[Dim0][Dim1] = words2[Dim0][Dim1];
                    }
                }
            }
            words2 = tempVar5;
            String[][] tempVar6 = new String[2][previewLength];
            if (wordsB != null){
                for (int Dim0 = 0; Dim0 < wordsB.length; Dim0++){
                    int CopyLength = Math.min(wordsB[0].length, tempVar6[0].length);
                    for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                        tempVar6[Dim0][Dim1] = wordsB[Dim0][Dim1];
                    }
                }
            }
            wordsB = tempVar6;
        }

        //parse input
        String[][] words1 = null; //non-punctuated
        words1 = new String[3][0];
        j = 0;
        for (int i = 0; i <= sent1.length() - 2; i++){
            if (sent1.substring(i,i+1).equals(" ") && !sent1.substring(i+1,i+2).equals(" ")){
                String[][] tempVar7;
                if (words1 != null){
                    tempVar7 = new String[3][words1[0].length + 1];
                    for (int Dim0 = 0; Dim0 < words1.length; Dim0++){
                        int CopyLength = Math.min(words1[0].length, tempVar7[0].length);
                        for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                            tempVar7[Dim0][Dim1] = words1[Dim0][Dim1];
                        }
                    }
                }
                else {
                    tempVar7 = new String[3][1];
                }
                words1 = tempVar7;
                words1[0][words1[0].length-1] = sent1.substring(j, i).trim();
                words1[1][words1[0].length-1] = Integer.toString(j);
                j = i + 1;
            }
        }
        String[][] tempVar8;
        if (words1 != null){
            tempVar8 = new String[3][words1[0].length + 1];
            for (int Dim0 = 0; Dim0 < words1.length; Dim0++){
                int CopyLength = Math.min(words1[0].length, tempVar8[0].length);
                for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                    tempVar8[Dim0][Dim1] = words1[Dim0][Dim1];
                }
            }
        }
        else{
            tempVar8 = new String[3][1];
        }
        words1 = tempVar8;
        words1[0][words1[0].length-1] = sent1.substring(j, sent1.length()).trim();
        words1[1][words1[0].length-1] = Integer.toString(j);

        String[][] wordsA = null; //punctuated
        wordsA = new String[2][0];
        j = 0;
        for (int i = 0; i <= sentA.length()-2; i++){
            if (sentA.substring(i,i+1).equals(" ") && !sentA.substring(i+1,i+2).equals(" ")){
                String[][] tempVar9;
                if (wordsA != null){
                    tempVar9 = new String[2][wordsA[0].length + 1];
                    for (int Dim0 = 0; Dim0 < wordsA.length; Dim0++){
                        int CopyLength = Math.min(wordsA[0].length, tempVar9[0].length);
                        for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                            tempVar9[Dim0][Dim1] = wordsA[Dim0][Dim1];
                        }
                    }
                }
                else {
                    tempVar9 = new String[2][1];
                }
                wordsA = tempVar9;
                wordsA[0][wordsA[0].length-1] = sentA.substring(j,i).trim();
                wordsA[1][wordsA[0].length-1] = Integer.toString(j);
                j = i + 1;
            }
        }
        String[][] tempVar10;
        if (wordsA != null){
            tempVar10 = new String[2][wordsA[0].length +1];
            for (int Dim0 = 0; Dim0 < wordsA.length; Dim0++){
                int CopyLength = Math.min(wordsA[0].length, tempVar10[0].length);
                for (int Dim1 = 0; Dim1 < CopyLength; Dim1++){
                    tempVar10[Dim0][Dim1] = wordsA[Dim0][Dim1];
                }
            }
        }
        else {
            tempVar10 = new String[2][1];
        }
        wordsA = tempVar10;
        wordsA[0][wordsA[0].length-1] = sentA.substring(j, sentA.length()).trim();
        wordsA[1][wordsA[0].length-1] = Integer.toString(j);

        //check for words in #2 not in #1
        for (int i = 0; i <= words2[0].length-1; i++){
            if (words2[0][i] != null){
                j = sent1.indexOf(words2[0][i]);
                if (j == -1){
                    words2[2][i] = "1"; //extra words
                }
                else {
                    words2[2][i] = "0"; //correct word
                }
            }
            else{
                words2[2][i] = "0";
            }
        }
        //check for words in #1 not in #2
        for (int i = 0; i <= words1[0].length-1;i++){
            j = sent2.indexOf(words1[0][i]);
            if (j == -1){
                words1[2][i] = "1"; //missing word
            }
            else{
                words1[2][i] = "0"; //correct word
            }
        }
        //check for order of words
        for (int i = 0; i <= words2[0].length-1;i++){
            words2[3][i] = "1"; //order match not found
            if (words2[0][i] != null){
                for (j = 0; j <= words1[0].length-1;j++){
                    if (words2[0][i].equals(words1[0][j])){
                        //word match. check if previous word matches
                        if (i == 0 && j == 0){
                            words2[3][i] = "0"; //first words is same
                            break;
                        }
                        else if (i > 0 && j > 0){
                            if (words2[0][i-1].equals(words1[0][j-1])){
                                words2[3][i] = "0"; //previous word matches
                                break;
                            }
                        }
                    }
                }
            }
            else{
                words2[3][i] = "0";
            }
        }

        //color output
        txtVerse = "";
        //FontStyel temp_bold = FontStyle.Regular;
        //Color temp_color = Color.Black;
        for (int i = 0; i <= words2[0].length-1;i++){
            if (wordsB[0][i] != null){
                if (words2[3][i] == "0"){
                    //words correct order
                    //temp_bold = regular
                    //temp_color = green
                }
                else if (words2[2][i] == "0"){
                    //word found
                    //temp_bold = regular
                    //temp_color = black
                    wordsB[0][i] = "_" + wordsB[0][i] + "_";
                }
                else {
                    //word not found
                    //temp_bold = bold
                    //temp_color = red
                    wordsB[0][i] = "*" + wordsB[0][i] + "*";
                }
                txtVerse += wordsB[0][i] + " ";
            }
        }
        //color input
        txtUser = "";
        for (int i = 0; i <= words1[0].length-1;i++){
            if (words1[2][i].equals("0")) {
                // word found
                //temp_bold = regular
                //temp_color = black
            }
            else {
                //word not found
                //temp_bold = bold
                //temp_color = red
                wordsA[0][i] = "*" + wordsA[0][i] + "*";
            }
            txtUser +=  wordsA[0][i] + " " ;
        }

        //calculate score
        int word_count = 0;
        int order_count = 0;
        double word_pct = 0;
        double order_pct = 0;
        for (int i = 0; i <= words2[0].length-1;i++){
            if (words2[2][i].equals("0")){
                word_count += 1;
            }
        }
        for (int i = 0; i <= words2[0].length-1;i++){
            if (words2[3][i].equals("0")){
                order_count += 1;
            }
        }
        word_pct = (double)word_count/(words2[0].length)*10000;
        order_pct = (double)order_count/(words2[0].length)*10000;
        word_pct = Math.round(word_pct);
        order_pct = Math.round(order_pct);
        word_pct /= 100;
        order_pct /= 100;
        wordScore = word_pct + "%";
        orderScore = order_pct + "%";

    }

    public static void fiveWords(String verseText){
        String fiveWords = "";
        String[][] wordsB = null; //punctuated
        String sentB = verseText;
        int j = 0;
        wordsB = new String[2][0];
        for (int i = 0; i <= sentB.length() - 2; i ++){
            if (sentB.substring(i,i+1).equals(" ") && !sentB.substring(i+1,i+2).equals(" ")){
                String[][] tempVar3;
                if (wordsB != null){
                    tempVar3 = new String[2][wordsB[0].length +1];
                    for (int Dimension0 = 0; Dimension0 < wordsB.length; Dimension0++){
                        int CopyLength = Math.min(wordsB[0].length,tempVar3[0].length);
                        for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                            tempVar3[Dimension0][Dimension1] = wordsB[Dimension0][Dimension1];
                        }
                    }
                }
                else{
                    tempVar3 = new String[2][1];
                }
                wordsB = tempVar3;
                wordsB[0][wordsB[0].length-1] = sentB.substring(j,i).trim();
                wordsB[1][wordsB[0].length-1] = Integer.toString(j);
                j = i + 1;
            }
        }

        String[][] tempVar4;
        if (wordsB != null){
            tempVar4 = new String[2][wordsB[0].length+1];
            for (int Dimension0 = 0; Dimension0 < wordsB.length; Dimension0++){
                int CopyLength = Math.min(wordsB[0].length, tempVar4[0].length);
                for (int Dimension1 = 0; Dimension1 < CopyLength; Dimension1++){
                    tempVar4[Dimension0][Dimension1] = wordsB[Dimension0][Dimension1];
                }
            }
        }
        else{
            tempVar4 = new String[2][1];
        }
        wordsB = tempVar4;
        wordsB[0][wordsB[0].length-1] = sentB.substring(j, sentB.length()).trim();
        wordsB[1][wordsB[1].length-1] = Integer.toString(j);
        //int limit = 5;
        if (wordsB[0].length < previewLength){
            previewLength = wordsB[0].length;
        }
        for (int i = 0; i < previewLength; i ++){
            fiveWords += wordsB[0][i] + " ";
        }
        fiveWords = fiveWords.trim();
        fiveWordsText = fiveWords;
    }

    public String getUser(){
        return txtUser;
    }
    public String getVerse(){
        return txtVerse;
    }
    public String getWordScore(){
        return wordScore;
    }
    public String getOrderScore(){
        return orderScore;
    }
    public static String getFiveWords(){
        return fiveWordsText;
    }
}
