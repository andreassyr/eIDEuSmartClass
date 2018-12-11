/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.generators;



/**
 *
 * @author nikos
 */
public class Translator {

    public static String translateGreekLetter(char letter) {
        switch (letter) {
            case 'Α':
                return "A";
            case 'Β':
                return "B";
            case 'Γ':
                return "C";
            case 'Δ':
                return "D";
            case 'Ε':
                return "E";
            case 'Ζ':
                return "Z";
            case 'Η':
                return "H";
            case 'Θ':
                return "TH";
            case 'Ι':
                return "I";
            case 'Κ':
                return "K";
            case 'Λ':
                return "L";
            case 'Μ':
                return "M";
            case 'Ν':
                return "N";
            case 'Ξ':
                return "X";
            case 'Ο':
                return "O";
            case 'Π':
                return "P";
            case 'Ρ':
                return "R";
            case 'Σ':
                return "S";
            case 'σ':
                return "S";
            case 'Τ':
                return "T";
            case 'Υ':
                return "Y";
            case 'Φ':
                return "FI";
            case 'Χ':
                return "CH";
            case 'Ψ':
                return "PS";
            case 'Ω':
                return "O";
            
            case 'ά':
            case 'Ά':    
                return "A";
            case 'έ':
            case 'Έ':
                return "E";
            case 'ή': 
            case 'Ή':    
                return "H";
            case 'ί' :
            case 'Ί':
                return "I";
            case 'ύ':
            case 'Ύ':
                return "Y";
            case 'ό':
            case 'Ό':    
                return "O";
            case 'ώ':
            case 'Ώ':    
                return "O";
            default:
                return letter+"";
        }
    }
    
    
    public static String translateGreekWordToEnglishAlphaBet(String word){
        StringBuilder sb =  new StringBuilder();
        word = word.toUpperCase();
        for(char letter: word.toCharArray()){
            sb.append(translateGreekLetter(letter));
        }
        return sb.toString();
    }

}
