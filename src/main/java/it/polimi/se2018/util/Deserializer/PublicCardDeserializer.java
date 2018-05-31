package it.polimi.se2018.util.Deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.util.jsonTransiction;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PublicCardDeserializer extends StrategyCardDeserializer {

    private ArrayList<jsonTransiction> publCard;
    private ColorDiagonalsStrategyBuilder cdsBuilder;
    private ColorVarietyStrategyBuilder cvsBuilder;
    private ColumnColorVarietyStrategyBuilder ccvsBuilder;
    private ColumnShadeVarietyStrategyBuilder csvsBuilder;
    private DeepShadesStrategyBuilder dssBuilder;
    private LightShadesStrategyBuilder lssBuilder;
    private MediumShadesStrategyBuilder mssBuilder;
    private RowColorVarietyStrategyBuilder rcvsBuilder;
    private RowShadeVarietyStrategyBuilder rsvsBuilder;
    private ShadeVarietyStrategyBuilder svsBuilder;
    private ArrayList<PublicObjectiveCard> publicObjectivetransfer;

    public PublicCardDeserializer(String pathname){
        super(pathname);
        cdsBuilder = new ColorDiagonalsStrategyBuilder();
        cvsBuilder = new ColorVarietyStrategyBuilder();
        ccvsBuilder = new ColumnColorVarietyStrategyBuilder();
        csvsBuilder = new ColumnShadeVarietyStrategyBuilder();
        dssBuilder = new DeepShadesStrategyBuilder();
        lssBuilder = new LightShadesStrategyBuilder();
        mssBuilder = new MediumShadesStrategyBuilder();
        rcvsBuilder = new RowColorVarietyStrategyBuilder();
        rsvsBuilder = new RowShadeVarietyStrategyBuilder();
        svsBuilder = new ShadeVarietyStrategyBuilder();
        publicObjectivetransfer = new ArrayList();
    }
    @Override
    public void SetUpObserver() {
        this.addObserver(cdsBuilder);
        this.addObserver(cvsBuilder);
        this.addObserver(ccvsBuilder);
        this.addObserver(csvsBuilder);
        this.addObserver(dssBuilder);
        this.addObserver(lssBuilder);
        this.addObserver(mssBuilder);
        this.addObserver(rcvsBuilder);
        this.addObserver(rsvsBuilder);
        this.addObserver(svsBuilder);
    }

    public void Deserializing(){
        Type trans = new TypeToken<ArrayList<jsonTransiction>>(){}.getType();
        jsontrans = this.getGson().fromJson(this.getBr(), trans);
    }
}
