package de.telran.processor.factory;

import de.telran.processor.service.ActionsConfigService;
import de.telran.processor.action.ImageAction;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ImageActionFactory {
    private ActionsConfigService configService;

    private Map<String, ImageAction> imageActionsMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ImageActionFactory factory = new ImageActionFactory(new ActionsConfigService());

        ImageAction preview = factory.getAction("PREVIEW");
        preview.doAction(null);
        ImageAction grayscale = factory.getAction("GRAYSCALE");
        grayscale.doAction(null);
        ImageAction newAction = factory.getAction("NEW ACTION");
        newAction.doAction(null);
        ImageAction aDefault = factory.getAction("DEFAULT");
        aDefault.doAction(null);
    }

    public ImageActionFactory(ActionsConfigService configService) throws Exception {
        this.configService = configService;

        List<String> actionClassNames = configService.getActionClassNames();
        String actionPackage = configService.getActionPackage();

        for (String className : actionClassNames) {
            //reflection API - создание объекта заданного класса, зная имя класса
            ImageAction imageAction = (ImageAction) Class.forName(actionPackage + "." + className)
                    .getConstructor().newInstance();
            imageActionsMap.put(imageAction.getName(), imageAction);
        }
    }

    public ImageAction getAction(String actionName) {
        //замена выбора action вместо switch case
        ImageAction imageAction = imageActionsMap.get(actionName);
        return imageAction;
    }
}
