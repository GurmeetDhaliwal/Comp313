package com.travelplan.app;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.travelplan.expandListView.Adapter.ExpandListAdapter;

public class ManualNavigationScreen extends Activity {
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_navigation_screen);
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(ManualNavigationScreen.this, ExpListItems);
        ExpandList.setAdapter((ExpandableListAdapter) ExpAdapter);
    }
    public ArrayList<ExpandListGroup> SetStandardGroups() {

        ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
        ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("Alberta");

            ExpandListChild ch1_1 = new ExpandListChild();
            ch1_1.setName("Medicine Hat");
            ch1_1.setTag(null);
            list2.add(ch1_1);

            ExpandListChild ch1_2 = new ExpandListChild();
            ch1_2.setName("Brooks");
            ch1_2.setTag(null);
            list2.add(ch1_2);

            ExpandListChild ch1_3 = new ExpandListChild();
            ch1_3.setName("Lethbridge");
            ch1_3.setTag(null);
            list2.add(ch1_3);

            ExpandListChild ch1_4 = new ExpandListChild();
            ch1_4.setName("Fort McMurray");
            ch1_4.setTag(null);
            list2.add(ch1_4);

            ExpandListChild ch1_5 = new ExpandListChild();
            ch1_5.setName("Wetaskiwin");
            ch1_5.setTag(null);
            list2.add(ch1_5);

            ExpandListChild ch1_6 = new ExpandListChild();
            ch1_6.setName("Edmonton");
            ch1_6.setTag(null);
            list2.add(ch1_6);

            ExpandListChild ch1_7 = new ExpandListChild();
            ch1_7.setName("Fort Saskatchewan");
            ch1_7.setTag(null);
            list2.add(ch1_7);

            ExpandListChild ch1_8 = new ExpandListChild();
            ch1_8.setName("Lloydminster");
            ch1_8.setTag(null);
            list2.add(ch1_8);

            ExpandListChild ch1_9 = new ExpandListChild();
            ch1_9.setName("Cold Lake");
            ch1_9.setTag(null);
            list2.add(ch1_9);

            ExpandListChild ch1_10 = new ExpandListChild();
            ch1_10.setName("Camrose");
            ch1_10.setTag(null);
            list2.add(ch1_10);

            ExpandListChild ch1_11 = new ExpandListChild();
            ch1_11.setName("Red Deer");
            ch1_11.setTag(null);
            list2.add(ch1_11);

            ExpandListChild ch1_12 = new ExpandListChild();
            ch1_12.setName("Grande Prairie");
            ch1_12.setTag(null);
            list2.add(ch1_12);

            ExpandListChild ch1_13 = new ExpandListChild();
            ch1_13.setName("Leduc");
            ch1_13.setTag(null);
            list2.add(ch1_13);

            ExpandListChild ch1_14 = new ExpandListChild();
            ch1_14.setName("St. Albert");
            ch1_14.setTag(null);
            list2.add(ch1_14);

            ExpandListChild ch1_15 = new ExpandListChild();
            ch1_15.setName("Calgary");
            ch1_15.setTag(null);
            list2.add(ch1_15);

            ExpandListChild ch1_16 = new ExpandListChild();
            ch1_16.setName("Spruce Grove");
            ch1_16.setTag(null);
            list2.add(ch1_16);

            ExpandListChild ch1_17 = new ExpandListChild();
            ch1_17.setName("Lacombe");
            ch1_17.setTag(null);
            list2.add(ch1_17);

        gru1.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru2 = new ExpandListGroup();
        gru2.setName("British Columbia");

            ExpandListChild ch2_1 = new ExpandListChild();
            ch2_1.setName("Prince Rupert");
            ch2_1.setTag(null);
            list2.add(ch2_1);

            ExpandListChild ch2_2 = new ExpandListChild();
            ch2_2.setName("Tofino");
            ch2_2.setTag(null);
            list2.add(ch2_2);

            ExpandListChild ch2_3 = new ExpandListChild();
            ch2_3.setName("Nanaimo");
            ch2_3.setTag(null);
            list2.add(ch2_3);

            ExpandListChild ch2_4 = new ExpandListChild();
            ch2_4.setName("Victoria");
            ch2_4.setTag(null);
            list2.add(ch2_4);

            ExpandListChild ch2_5 = new ExpandListChild();
            ch2_5.setName("Vancouver");
            ch2_5.setTag(null);
            list2.add(ch2_5);

            ExpandListChild ch2_6 = new ExpandListChild();
            ch2_6.setName("Chilliwack");
            ch2_6.setTag(null);
            list2.add(ch2_6);

            ExpandListChild ch2_7 = new ExpandListChild();
            ch2_7.setName("Penticton");
            ch2_7.setTag(null);
            list2.add(ch2_7);

            ExpandListChild ch2_8 = new ExpandListChild();
            ch2_8.setName("Kamloops");
            ch2_8.setTag(null);
            list2.add(ch2_8);

            ExpandListChild ch2_9 = new ExpandListChild();
            ch2_9.setName("Prince George");
            ch2_9.setTag(null);
            list2.add(ch2_9);

        gru2.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru3 = new ExpandListGroup();
        gru3.setName("Manitoba");

            ExpandListChild ch3_1 = new ExpandListChild();
            ch3_1.setName("Morden");
            ch3_1.setTag(null);
            list2.add(ch3_1);

            ExpandListChild ch3_2=new ExpandListChild();
            ch3_2.setName("Winnipeg");
            ch3_2.setTag(null);
            list2.add(ch3_2);

            ExpandListChild ch3_3=new ExpandListChild();
            ch3_3.setName("Pierson");
            ch3_3.setTag(null);
            list2.add(ch3_3);

            ExpandListChild ch3_4=new ExpandListChild();
            ch3_4.setName("Dauphin");
            ch3_4.setTag(null);
            list2.add(ch3_4);

            ExpandListChild ch3_5=new ExpandListChild();
            ch3_5.setName("Steinbach");
            ch3_5.setTag(null);
            list2.add(ch3_5);

            ExpandListChild ch3_6=new ExpandListChild();
            ch3_6.setName("Portage la Prairie");
            ch3_6.setTag(null);
            list2.add(ch3_6);

            ExpandListChild ch3_7=new ExpandListChild();
            ch3_7.setName("Brandon");
            ch3_7.setTag(null);
            list2.add(ch3_7);

            ExpandListChild ch3_8=new ExpandListChild();
            ch3_8.setName("The Pas");
            ch3_8.setTag(null);
            list2.add(ch3_8);

            ExpandListChild ch3_9=new ExpandListChild();
            ch3_9.setName("Thompson");
            ch3_9.setTag(null);
            list2.add(ch3_9);

            ExpandListChild ch3_10=new ExpandListChild();
            ch3_10.setName("Churchill");
            ch3_10.setTag(null);
            list2.add(ch3_10);

        gru3.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru4 = new ExpandListGroup();
        gru4.setName("New Brunswick");

            ExpandListChild ch4_1 = new ExpandListChild();
            ch4_1.setName("Saint John ");
            ch4_1.setTag(null);
            list2.add(ch4_1);

            ExpandListChild ch4_2=new ExpandListChild();
            ch4_2.setName("Coast");
            ch4_2.setTag(null);
            list2.add(ch4_2);

            ExpandListChild ch4_3=new ExpandListChild();
            ch4_3.setName("Pierson");
            ch4_3.setTag(null);
            list2.add(ch4_3);

            ExpandListChild ch4_4=new ExpandListChild();
            ch4_4.setName("Fredericton");
            ch4_4.setTag(null);
            list2.add(ch3_4);

        gru4.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru5 = new ExpandListGroup();
        gru5.setName("Newfoundland and Labrador");

            ExpandListChild ch5_1 = new ExpandListChild();
            ch5_1.setName("St. John’s");
            ch5_1.setTag(null);
            list2.add(ch5_1);

            ExpandListChild ch5_2=new ExpandListChild();
            ch5_2.setName("Gander");
            ch5_2.setTag(null);
            list2.add(ch5_2);

            ExpandListChild ch5_3=new ExpandListChild();
            ch5_3.setName("Corner Brook");
            ch5_3.setTag(null);
            list2.add(ch5_3);

            ExpandListChild ch5_4=new ExpandListChild();
            ch5_4.setName("Stephenville");
            ch5_4.setTag(null);
            list2.add(ch5_4);

            ExpandListChild ch5_5=new ExpandListChild();
            ch5_5.setName("Happy Valley – Goose Bay");
            ch5_5.setTag(null);
            list2.add(ch5_5);

            ExpandListChild ch5_6=new ExpandListChild();
            ch5_6.setName("Nain");
            ch5_6.setTag(null);
            list2.add(ch5_6);

        gru5.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru6 = new ExpandListGroup();
        gru6.setName("Nova Scotia");

            ExpandListChild ch6_1 = new ExpandListChild();
            ch6_1.setName("Cape Breton");
            ch6_1.setTag(null);
            list2.add(ch6_1);

            ExpandListChild ch6_2=new ExpandListChild();
            ch6_2.setName("Halifax");
            ch6_2.setTag(null);
            list2.add(ch6_2);

            ExpandListChild ch6_3=new ExpandListChild();
            ch6_3.setName("Queens");
            ch6_3.setTag(null);
            list2.add(ch6_3);

        gru6.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru7 = new ExpandListGroup();
        gru7.setName("Ontario");

            ExpandListChild ch7_1 = new ExpandListChild();
            ch7_1.setName("Windsor");
            ch7_1.setTag(null);
            list2.add(ch7_1);

            ExpandListChild ch7_2=new ExpandListChild();
            ch7_2.setName("Niagara Falls");
            ch7_2.setTag(null);
            list2.add(ch7_2);

            ExpandListChild ch7_3=new ExpandListChild();
            ch7_3.setName("Toronto");
            ch7_3.setTag(null);
            list2.add(ch7_3);

            ExpandListChild ch7_4=new ExpandListChild();
            ch7_4.setName("Midland");
            ch7_4.setTag(null);
            list2.add(ch7_4);

            ExpandListChild ch7_5=new ExpandListChild();
            ch7_5.setName("Ottawa");
            ch7_5.setTag(null);
            list2.add(ch7_5);

            ExpandListChild ch7_6=new ExpandListChild();
            ch7_6.setName("Sudbury");
            ch7_6.setTag(null);
            list2.add(ch7_6);

            ExpandListChild ch7_7=new ExpandListChild();
            ch7_7.setName("Thunder Bay");
            ch7_7.setTag(null);
            list2.add(ch7_7);

            ExpandListChild ch7_8=new ExpandListChild();
            ch7_8.setName("Kenora");
            ch7_8.setTag(null);
            list2.add(ch7_8);

            ExpandListChild ch7_9=new ExpandListChild();
            ch7_9.setName("Moosonee");
            ch7_9.setTag(null);
            list2.add(ch7_9);

        gru7.setItems(list2);


        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru8 = new ExpandListGroup();
        gru8.setName("Prince Edward Island");

            ExpandListChild ch8_1 = new ExpandListChild();
            ch8_1.setName("Alberton");
            ch8_1.setTag(null);
            list2.add(ch8_1);

            ExpandListChild ch8_2=new ExpandListChild();
            ch8_2.setName("Borden-Carleton");
            ch8_2.setTag(null);
            list2.add(ch8_2);

            ExpandListChild ch8_3=new ExpandListChild();
            ch8_3.setName("Cornwall");
            ch8_3.setTag(null);
            list2.add(ch8_3);

            ExpandListChild ch8_4=new ExpandListChild();
            ch8_4.setName("Georgetown");
            ch8_4.setTag(null);
            list2.add(ch8_4);

            ExpandListChild ch8_5=new ExpandListChild();
            ch8_5.setName("Kensington");
            ch8_5.setTag(null);
            list2.add(ch8_5);

            ExpandListChild ch8_6=new ExpandListChild();
            ch8_6.setName("Montague");
            ch8_6.setTag(null);
            list2.add(ch8_6);

            ExpandListChild ch8_7=new ExpandListChild();
            ch8_7.setName("North Rustico");
            ch8_7.setTag(null);
            list2.add(ch8_7);

            ExpandListChild ch8_8=new ExpandListChild();
            ch8_8.setName("Souris");
            ch8_8.setTag(null);
            list2.add(ch8_8);

            ExpandListChild ch8_9=new ExpandListChild();
            ch8_9.setName("Stratford");
            ch8_9.setTag(null);
            list2.add(ch8_9);

        gru8.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru9 = new ExpandListGroup();
        gru9.setName("Quebec");

            ExpandListChild ch9_1 = new ExpandListChild();
            ch9_1.setName("Montreal");
            ch9_1.setTag(null);
            list2.add(ch9_1);

            ExpandListChild ch9_2=new ExpandListChild();
            ch9_2.setName("Quebec City");
            ch9_2.setTag(null);
            list2.add(ch9_2);

            ExpandListChild ch9_3=new ExpandListChild();
            ch9_3.setName("Gatineau");
            ch9_3.setTag(null);
            list2.add(ch9_3);

            ExpandListChild ch9_4=new ExpandListChild();
            ch9_4.setName("Sherbrooke");
            ch9_4.setTag(null);
            list2.add(ch9_4);

            ExpandListChild ch9_5=new ExpandListChild();
            ch9_5.setName("Saguenay");
            ch9_5.setTag(null);
            list2.add(ch9_5);

            ExpandListChild ch9_6=new ExpandListChild();
            ch9_6.setName("Trois-Rivières");
            ch9_6.setTag(null);
            list2.add(ch9_6);

            ExpandListChild ch9_7=new ExpandListChild();
            ch9_7.setName("Saint-Jean-sur-Richelieu");
            ch9_7.setTag(null);
            list2.add(ch9_7);

            ExpandListChild ch9_8=new ExpandListChild();
            ch9_8.setName("Drummondville");
            ch9_8.setTag(null);
            list2.add(ch9_8);

            ExpandListChild ch9_9=new ExpandListChild();
            ch9_9.setName("Granby");
            ch9_9.setTag(null);
            list2.add(ch9_9);

            ExpandListChild ch9_10=new ExpandListChild();
            ch9_10.setName("Saint-Hyacinthe");
            ch9_10.setTag(null);
            list2.add(ch9_10);

            ExpandListChild ch9_11 = new ExpandListChild();
            ch9_11.setName("Shawinigan");
            ch9_11.setTag(null);
            list2.add(ch9_11);

            ExpandListChild ch9_12=new ExpandListChild();
            ch9_12.setName("Rimouski");
            ch9_12.setTag(null);
            list2.add(ch9_12);

            ExpandListChild ch9_13=new ExpandListChild();
            ch9_13.setName("Sorel-Tracy");
            ch9_13.setTag(null);
            list2.add(ch9_13);

            ExpandListChild ch9_14=new ExpandListChild();
            ch9_14.setName("Joliette");
            ch9_14.setTag(null);
            list2.add(ch9_14);

            ExpandListChild ch9_15=new ExpandListChild();
            ch9_15.setName("Victoriaville");
            ch9_15.setTag(null);
            list2.add(ch9_15);

            ExpandListChild ch9_16=new ExpandListChild();
            ch9_16.setName("Rouyn-Noranda");
            ch9_16.setTag(null);
            list2.add(ch9_16);

            ExpandListChild ch9_17=new ExpandListChild();
            ch9_17.setName("Salaberry-de-Valleyfield");
            ch9_17.setTag(null);
            list2.add(ch9_17);

            ExpandListChild ch9_18=new ExpandListChild();
            ch9_18.setName("Saint-Georges");
            ch9_18.setTag(null);
            list2.add(ch9_18);

            ExpandListChild ch9_19=new ExpandListChild();
            ch9_19.setName("Val-d'Or");
            ch9_19.setTag(null);
            list2.add(ch9_19);

            ExpandListChild ch9_20=new ExpandListChild();
            ch9_20.setName("Alma");
            ch9_20.setTag(null);
            list2.add(ch9_20);

            ExpandListChild ch9_21 = new ExpandListChild();
            ch9_21.setName("Baie-Comeau");
            ch9_21.setTag(null);
            list2.add(ch9_21);

            ExpandListChild ch9_22=new ExpandListChild();
            ch9_22.setName("Sept-Îles");
            ch9_22.setTag(null);
            list2.add(ch9_22);

            ExpandListChild ch9_23=new ExpandListChild();
            ch9_23.setName("Thetford Mines");
            ch9_23.setTag(null);
            list2.add(ch9_23);

            ExpandListChild ch9_24=new ExpandListChild();
            ch9_24.setName("Rivière-du-Loup");
            ch9_24.setTag(null);
            list2.add(ch9_24);

            ExpandListChild ch9_25=new ExpandListChild();
            ch9_25.setName("Matane");
            ch9_25.setTag(null);
            list2.add(ch9_25);

            ExpandListChild ch9_26=new ExpandListChild();
            ch9_26.setName("Amos");
            ch9_26.setTag(null);
            list2.add(ch9_26);

            ExpandListChild ch9_27=new ExpandListChild();
            ch9_27.setName("Dolbeau-Mistassini");
            ch9_27.setTag(null);
            list2.add(ch9_27);

            ExpandListChild ch9_28=new ExpandListChild();
            ch9_28.setName("La Tuque");
            ch9_28.setTag(null);
            list2.add(ch9_28);

            ExpandListChild ch9_29=new ExpandListChild();
            ch9_29.setName("Lachute");
            ch9_29.setTag(null);
            list2.add(ch9_29);

            ExpandListChild ch9_30=new ExpandListChild();
            ch9_30.setName("Cowansville");
            ch9_30.setTag(null);
            list2.add(ch9_30);

        gru9.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();

        ExpandListGroup gru10 = new ExpandListGroup();
        gru10.setName("Saskatchewan");

            ExpandListChild ch10_1 = new ExpandListChild();
            ch10_1.setName("Maple Creek");
            ch10_1.setTag(null);
            list2.add(ch10_1);

            ExpandListChild ch10_2 = new ExpandListChild();
            ch10_2.setName("Estevan");
            ch10_2.setTag(null);
            list2.add(ch10_2);

            ExpandListChild ch10_3 = new ExpandListChild();
            ch10_3.setName("Weyburn");
            ch10_3.setTag(null);
            list2.add(ch10_3);

            ExpandListChild ch10_4 = new ExpandListChild();
            ch10_4.setName("Moose Jaw");
            ch10_4.setTag(null);
            list2.add(ch10_4);

            ExpandListChild ch10_5 = new ExpandListChild();
            ch10_5.setName("Regina");
            ch10_5.setTag(null);
            list2.add(ch10_5);

            ExpandListChild ch10_6 = new ExpandListChild();
            ch10_6.setName("Saskatoon");
            ch10_6.setTag(null);
            list2.add(ch10_6);

            ExpandListChild ch10_7 = new ExpandListChild();
            ch10_7.setName("Melville");
            ch10_7.setTag(null);
            list2.add(ch10_7);

            ExpandListChild ch10_8 = new ExpandListChild();
            ch10_8.setName("Swift Current");
            ch10_8.setTag(null);
            list2.add(ch10_8);

            ExpandListChild ch10_9 = new ExpandListChild();
            ch10_9.setName("Humboldt");
            ch10_9.setTag(null);
            list2.add(ch10_9);

            ExpandListChild ch10_10 = new ExpandListChild();
            ch10_10.setName("Melfort");
            ch10_10.setTag(null);
            list2.add(ch10_10);

            ExpandListChild ch10_11 = new ExpandListChild();
            ch10_11.setName("North Battleford");
            ch10_11.setTag(null);
            list2.add(ch10_11);

            ExpandListChild ch10_12 = new ExpandListChild();
            ch10_12.setName("Yorkton");
            ch10_12.setTag(null);
            list2.add(ch10_12);

            ExpandListChild ch10_13 = new ExpandListChild();
            ch10_13.setName("Prince Albert");
            ch10_13.setTag(null);
            list2.add(ch10_13);

        gru10.setItems(list2);

        list.add(gru1);
        list.add(gru2);
        list.add(gru3);
        list.add(gru4);
        list.add(gru5);
        list.add(gru6);
        list.add(gru7);
        list.add(gru8);
        list.add(gru9);
        list.add(gru10);
        return list;
    }

}