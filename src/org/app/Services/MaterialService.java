package org.app.Services;

import org.app.Models.Repositories.RepositoriesImplementation.MaterialRepositoryImpl;
import org.views.material.MaterialView;

public class MaterialService {
    public MaterialRepositoryImpl model;
    public MaterialView view;

    MaterialService(MaterialRepositoryImpl model, MaterialView view) {
        this.model = model;
        this.view = view;
    }
}
