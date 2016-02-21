package com.example.rishab.moodle;

/**
 * Created by Paras Gupta on 20-02-2016.
 */
public class EncodeNotificationDescription{

    public static NotificationEncodedObject encode(String s){

        NotificationEncodedObject res = new NotificationEncodedObject();
        String link = "";
        String display = "";

        int length = s.length();

        int i = -1, j = -1;
        int it = 0;

        boolean flag1=false,flag2=false;

        while(it<length){

            if(s.charAt(it)=='>'||s.charAt(it)=='<')	{	flag1^=true;	if(flag1==false)	i++;	}
            else if(s.charAt(it)=='\'')	{	flag2^=true;	if(flag2==true)	j++;	}
            else {
                if(flag1==false)	display+=s.charAt(it);
                if(flag2==true&&j==1)	link+=s.charAt(it);
            }

            it++;
        }
        res.display = display;
        res.link = link;
        return res;
    }

}