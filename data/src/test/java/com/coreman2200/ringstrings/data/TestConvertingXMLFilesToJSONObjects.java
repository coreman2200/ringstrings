package com.coreman2200.ringstrings.data;

import android.app.Activity;

import com.coreman2200.ringstrings.app.activity.RingStringsActivity;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowResources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * TestConvertingXMLFilesToJSONObjects
 * Testing some ish ~ want to convert all of the xml files to json objects..
 *
 * Created by Cory Higginbottom on 11/4/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@RunWith(RobolectricTestRunner.class)

public class TestConvertingXMLFilesToJSONObjects {
    static final int gentRes = com.coreman2200.ringstrings.domain.R.raw.gent;
    private Activity mTestActivity;
    private ShadowResources mResources;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mResources = Shadows.shadowOf(mTestActivity.getResources());

    }

    //@Test
    public void testShadowResourcesCanProduceCorrectResourceNames() {

        String filename1 = mResources.getResourceName(gentRes);
        assert (!filename1.isEmpty());
    }

    //@Test
    public void testConvertXMLFileToJSONObject() {
        try {
            JSONObject jsonGent = produceJSONObjectFromGEnt();
            System.out.println(jsonGent.toString(4));
        } catch (JSONException e) {
            System.out.print("JSON exception" + e.getMessage());
            e.printStackTrace();
        }

    }

    private JSONObject produceJSONObjectFromGEnt() throws JSONException {
        InputStream gentIS = mResources.openRawResource(gentRes);
        assert(gentIS != null);

        String gentFile = writeInputStreamToString(gentIS);

        return XML.toJSONObject(gentFile);

    }

    @Test
    public void testProducingProperJSONSymbolManifestFromGEnt() {

        try {
            JSONObject jsonGent = produceJSONObjectFromGEnt();
            JSONObject symbols = produceSymbolManifestJSONObject(jsonGent);
            FileWriter file = new FileWriter("symboldefs.txt");
            file.write(symbols.toString(4));
            file.flush();
            file.close();

            //System.out.println(symbols.toString(4));
        } catch (JSONException e) {
            System.out.print("JSON exception" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("IO exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    private JSONObject produceSymbolManifestJSONObject(JSONObject gent) {
        JSONObject symbols = new JSONObject();

        try {
            JSONArray gentSymbols = gent.getJSONObject("resources").getJSONArray("Symbol");
            for (int i = 0; i < gentSymbols.length(); i++) {
                JSONObject oldsymbol = gentSymbols.getJSONObject(i);
                JSONObject symbol = new JSONObject();
                JSONObject quals = oldsymbol.getJSONObject("Quality");
                JSONArray tags = quals.optJSONArray("tag");

                if (tags == null)
                    tags = new JSONArray().put(quals.getString("tag"));

                for (int x = 0; x < tags.length(); x++) {
                    tags.put(x, tags.getString(x).toLowerCase(Locale.US));
                }

                symbol.put("Description", oldsymbol.get("Description"));
                symbol.put("Quality", tags);

                String name = oldsymbol.getString("id");
                symbol.put("Name", name);

                symbols.put(name.replaceAll("-","").replaceAll(" ","").toUpperCase(Locale.US), symbol);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            assert (false);
        }

        return symbols;
    }

    private String writeInputStreamToString(InputStream is) {
        try
        {
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            return new String(buffer);

        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            assert (false);
        }
        return null;
    }

}
