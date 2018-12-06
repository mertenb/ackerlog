import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INutrient } from 'app/shared/model/nutrient.model';

@Component({
    selector: 'jhi-nutrient-detail',
    templateUrl: './nutrient-detail.component.html'
})
export class NutrientDetailComponent implements OnInit {
    nutrient: INutrient;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nutrient }) => {
            this.nutrient = nutrient;
        });
    }

    previousState() {
        window.history.back();
    }
}
