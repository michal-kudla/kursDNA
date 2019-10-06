package com.m1k0.fastcorner.model_c4;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.documentation.Format;
import com.structurizr.documentation.StructurizrDocumentationTemplate;
import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.model.Enterprise;
import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.PaperSize;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

import java.io.StringWriter;

/**
 * Model C1
 * - Kto używa naszego modelu,
 * - z jakiego powodu on powstal
 * - jakie są integracje
 * - i jaka jest celowosc tych integracji
 */
public class C1
{

    private static final long WORKSPACE_ID = 47648;
    private static final String API_KEY = "fbdb4f9a-fa97-4506-aa7c-f8cad0370cd9";
    private static final String API_SECRET = "c1337b87-a555-4e30-8939-c798c3b9ebe0";

    private static final String DATABASE_TAG = "Database";


    public static void main(String[] args) throws Exception {

        // a Structurizr workspace is the wrapper for a software architecture model, views and documentation
        Workspace workspace = new Workspace("FastCorner", "C1 model of FastCorner  system.");
        Model model = workspace.getModel();

        model.setEnterprise(new Enterprise("Motosport Software"));

        // add some elements to your software architecture model
        Person emiterUser = model.addPerson(
                Location.Internal,
                "MobileUser",
                "A mobile User emitting geo-locations.");
        Person consumerUser = model.addPerson(
                Location.Internal,
                "ConsumerUser",
                "A consumer User consuming processed data.");

        SoftwareSystem softwareSystem = model.addSoftwareSystem(
                Location.Internal,
                "FastCorner",
                "FastCorner Software System.");
        emiterUser.uses(softwareSystem, "Emit GeoLocations");
        consumerUser.uses(softwareSystem, "Uses processed data");

        // define some views (the diagrams you would like to see)
        ViewSet views = workspace.getViews();
        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "FastCorner   C1 diagram.");
        contextView.setPaperSize(PaperSize.A5_Landscape);
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // add some documentation
        StructurizrDocumentationTemplate template = new StructurizrDocumentationTemplate(workspace);
        template.addContextSection(softwareSystem, Format.Markdown,
                "Here is some context about the software system...\n" +
                        "\n" +
                        "![](embed:SystemContext)");

        // add some styling
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        views.getConfiguration().getStyles().addElementStyle(DATABASE_TAG).shape(Shape.Cylinder);

        String plantString = toPlantUml(workspace);


        uploadWorkspaceToStructurizr(workspace);
    }

    private static String toPlantUml(Workspace workspace) {
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();
        StringWriter stringWriter = new StringWriter();
        plantUMLWriter.write(workspace, stringWriter);
        System.out.println(stringWriter.toString());
        return stringWriter.toString();
    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}
