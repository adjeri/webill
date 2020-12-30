/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.utils;

import java.util.HashMap;

/**
 *
 * @author Kanfitine
 */
public class Validator {
    public void validate(String testString, String key, String invalidMessage, String regexp, HashMap<String, String> errors){
        if (testString.matches(regexp)){
        } else{
             errors.put(key, invalidMessage);
        }
    }
}
