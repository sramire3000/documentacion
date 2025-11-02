# Angular
The framework for building scalable web apps with confidence

### Uninstall Angular
```bash
npm uninstall -g @angular/cli 
npm uninstall -g angular-cli
npm cache clean
npm cache clean --force 

# Eliminar carpeta windows
C:\Users\<usuario>\AppData\Roaming\npm\node_modules\@angular
```


### Versiones comunes y compatibilidad
```

Angular	Node.js	             TypeScript	npm	   Fecha Lanzamiento	Estado
v20	    18.19+ / 20.10+ / 22.0+	5.6+	8.11+  Mayo 2025	        PRÓXIMA VERSIÓN
v19	    18.19+ / 20.10+ / 22.0+	5.4+	8.11+  Nov 2024	            ÚLTIMA ESTABLE
v18	    18.19+ / 20.10+	        5.4+	8.11+  Mayo 2024	        LTS (hasta 2026)
v17	    18.13+ / 20.9+	        5.2+	8.11+  Nov 2023	            LTS (hasta 2025)
v16	    16.14+ / 18.10+	        4.9+	8.11+  Mayo 2023	        Fin soporte
```

### Install Angular CLI
To install or update nvm, you should run the install script. To do that, you may either download and run the script manually, or use the following cURL or Wget command:

### Install ultima version
```bash
npm install -g @angular/cli
ng version
```

### Install version especifica
```bash
npm install -g @angular/cli@<version>

Ejemplo:
  npm install -g @angular/cli@14.2.0
  npm install -g @angular/cli@15.1.0
  npm install -g @angular/cli@16.0.0
```

### Create Proyect
```bash
ng new <project-name>
```
### Install Dependencias
```bash
npm install
```

### Run
```bash
npm start
```

# Angular + PrimeNg + Tailwind
```bash
info:
  Angular CLI: 20.3.8
  Node: 24.3.0
  Package Manager: npm 11.4.2
  OS: linux x64
```
### Install Node / Angular
```bash
npm unistall -g @angular/cli
nvm list
nvm install v24.3.0 
nvm use v24.3.0
npm install -g @angular/cli@20.3.8
```
## Crear proyecto Angular
```bash 
ng new angular20-primeng-tallwind4
   StyleSheet format : CSS
   Prerendering          	: No
   Zoneless                 : Yes
   Practices					: claude, github copilot, Geminit 
   
cd angular20-primeng-tallwind4
ng serve --open
```

## Install Tallwind Css 4
### Install paquete de TailwindCss4
```bash
npm install tailwindcss @tailwindcss/postcss postcss --force
```

### Crear archivo en raiz  .postcssrc.json
```bash
{
  "plugins": {
    "@tailwindcss/postcss": {}
  }
}
```

### Add an @import to ./src/styles.css that imports Tailwind CSS.
```bash
@import "tailwindcss";
```

### Test good install tallwind ./src/app/app.html
```
<router-outlet />
<h1 class="text-3xl font-bold underline bg-amber-500">
  Hello world!
</h1>
```
### Test Run Tallwind
```bash
ng serve --open
```

## Install PrimeNG

### Install paquete PrimeNg
```bash
npm install primeng @primeuix/themes
npm install quill chart.js --save
npm install primeicons
```

### Provider
Add providePrimeNG and provideAnimationsAsync to the list of providers in your app.config.ts and use the theme property to configure a theme such as Aura.
```bash
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
//import Aura from '@primeuix/themes/aura';
import Lara from '@primeuix/themes/lara';

export const appConfig: ApplicationConfig = {
    providers: [
      // Add
      provideAnimationsAsync(),
      providePrimeNG({
          theme: {
              preset: Lara,
              options: {
                  prefix: 'p',
                  darkModeSelector: 'system',
                  cssLayer: false
              }
          }
      })
      // End Add
    ]
};
```

### Test PrimeNg in /src/app/app.ts
```bash
//Add
import { ButtonModule } from 'primeng/button';

//Add
imports: [ButtonModule],

```

### Test PrimeNg in /src/app/app.html
```bash
<p-button label="Check" />
<i class="pi pi-spin pi-spinner" style="font-size: 2rem"></i>
<i class="pi pi-spin pi-cog" style="font-size: 2rem"></i>
```

### Test Run PrimeNg
```bash
ng serve --open
```

### Install Adictional Prime UI
```bash
npm install tailwindcss-primeui
```
### Add archivo styles.css
```bash
@import "tailwindcss";
@import "tailwindcss-primeui";
@import "primeicons/primeicons.css";
```

### Add file "/src/app/prime-imports.ts"
```bash
import { AutoFocusModule } from 'primeng/autofocus';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { TabsModule } from 'primeng/tabs';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';
import { AccordionModule } from 'primeng/accordion';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BadgeModule } from 'primeng/badge';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { BlockUIModule } from 'primeng/blockui';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { CarouselModule } from 'primeng/carousel';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { ChartModule } from 'primeng/chart';
import { CheckboxModule } from 'primeng/checkbox';
import { ChipModule } from 'primeng/chip';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { DockModule } from 'primeng/dock';
import { DragDropModule } from 'primeng/dragdrop';
import { SelectModule } from 'primeng/select';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { EditorModule } from 'primeng/editor';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { FocusTrapModule } from 'primeng/focustrap';
import { GalleriaModule } from 'primeng/galleria';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InplaceModule } from 'primeng/inplace';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputOtpModule } from 'primeng/inputotp';
import { ImageModule } from 'primeng/image';
import { ImageCompareModule } from 'primeng/imagecompare';
import { KnobModule } from 'primeng/knob';
import { ListboxModule } from 'primeng/listbox';
import { MegaMenuModule } from 'primeng/megamenu';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MessageModule } from 'primeng/message';
import { MultiSelectModule } from 'primeng/multiselect';
import { MeterGroupModule } from 'primeng/metergroup';
import { OrganizationChartModule } from 'primeng/organizationchart';
import { OrderListModule } from 'primeng/orderlist';
import { PaginatorModule } from 'primeng/paginator';
import { PanelModule } from 'primeng/panel';
import { PanelMenuModule } from 'primeng/panelmenu';
import { PasswordModule } from 'primeng/password';
import { PickListModule } from 'primeng/picklist';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ProgressBarModule } from 'primeng/progressbar';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ScrollerModule } from 'primeng/scroller';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { ScrollTopModule } from 'primeng/scrolltop';
import { SkeletonModule } from 'primeng/skeleton';
import { SliderModule } from 'primeng/slider';
import { SpeedDialModule } from 'primeng/speeddial';
import { SplitterModule } from 'primeng/splitter';
import { StepperModule } from 'primeng/stepper';
import { SplitButtonModule } from 'primeng/splitbutton';
import { StepsModule } from 'primeng/steps';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TerminalModule } from 'primeng/terminal';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { TimelineModule } from 'primeng/timeline';
import { ToastModule } from 'primeng/toast';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { TreeModule } from 'primeng/tree';
import { TreeSelectModule } from 'primeng/treeselect';
import { TreeTableModule } from 'primeng/treetable';
import { CardModule } from 'primeng/card';
import { RippleModule } from 'primeng/ripple';
import { StyleClassModule } from 'primeng/styleclass';
import { FloatLabelModule } from 'primeng/floatlabel';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { DrawerModule } from 'primeng/drawer';
import { KeyFilterModule } from 'primeng/keyfilter';

@NgModule({
  imports: [
    AvatarModule,
    KeyFilterModule,
    AvatarGroupModule,
    AnimateOnScrollModule,
    TabsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AccordionModule,
    AutoCompleteModule,
    BadgeModule,
    BreadcrumbModule,
    BlockUIModule,
    ButtonModule,
    DatePickerModule,
    CarouselModule,
    CascadeSelectModule,
    ChartModule,
    CheckboxModule,
    ChipModule,
    ColorPickerModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    ContextMenuModule,
    DataViewModule,
    DialogModule,
    DividerModule,
    DrawerModule,
    DockModule,
    DragDropModule,
    SelectModule,
    DynamicDialogModule,
    EditorModule,
    FieldsetModule,
    FileUploadModule,
    FocusTrapModule,
    GalleriaModule,
    IftaLabelModule,
    InplaceModule,
    InputMaskModule,
    InputTextModule,
    TextareaModule,
    InputNumberModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputOtpModule,
    ImageModule,
    ImageCompareModule,
    KnobModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    MessageModule,
    MultiSelectModule,
    MeterGroupModule,
    OrganizationChartModule,
    OrderListModule,
    PaginatorModule,
    PanelModule,
    PanelMenuModule,
    PasswordModule,
    PickListModule,
    ProgressSpinnerModule,
    ProgressBarModule,
    RadioButtonModule,
    RatingModule,
    SelectButtonModule,
    ScrollerModule,
    ScrollPanelModule,
    ScrollTopModule,
    SkeletonModule,
    SliderModule,
    SpeedDialModule,
    SplitterModule,
    StepperModule,
    SplitButtonModule,
    StepsModule,
    TableModule,
    TagModule,
    TerminalModule,
    TieredMenuModule,
    TimelineModule,
    ToastModule,
    ToggleButtonModule,
    ToggleSwitchModule,
    ToolbarModule,
    TooltipModule,
    TreeModule,
    TreeSelectModule,
    TreeTableModule,
    CardModule,
    RippleModule,
    StyleClassModule,
    FloatLabelModule,
    IconFieldModule,
    InputIconModule,
    AutoFocusModule,
    OverlayBadgeModule,
  ],
  exports: [
    TabsModule,
    AvatarModule,
    AvatarGroupModule,
    AnimateOnScrollModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AccordionModule,
    AutoCompleteModule,
    BadgeModule,
    BreadcrumbModule,
    BlockUIModule,
    ButtonModule,
    DatePickerModule,
    CarouselModule,
    KeyFilterModule,
    CascadeSelectModule,
    ChartModule,
    CheckboxModule,
    ChipModule,
    ColorPickerModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    ContextMenuModule,
    DataViewModule,
    DialogModule,
    DividerModule,
    DrawerModule,
    DockModule,
    DragDropModule,
    SelectModule,
    DynamicDialogModule,
    EditorModule,
    FieldsetModule,
    FileUploadModule,
    FocusTrapModule,
    GalleriaModule,
    IftaLabelModule,
    InplaceModule,
    InputMaskModule,
    InputTextModule,
    TextareaModule,
    InputNumberModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputOtpModule,
    ImageModule,
    ImageCompareModule,
    KnobModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    MessageModule,
    MultiSelectModule,
    MeterGroupModule,
    OrganizationChartModule,
    OrderListModule,
    PaginatorModule,
    PanelModule,
    PanelMenuModule,
    PasswordModule,
    PickListModule,
    ProgressSpinnerModule,
    ProgressBarModule,
    RadioButtonModule,
    RatingModule,
    SelectButtonModule,
    ScrollerModule,
    ScrollPanelModule,
    ScrollTopModule,
    SkeletonModule,
    SliderModule,
    SpeedDialModule,
    SplitterModule,
    StepperModule,
    SplitButtonModule,
    StepsModule,
    TableModule,
    TagModule,
    TerminalModule,
    TieredMenuModule,
    TimelineModule,
    ToastModule,
    ToggleButtonModule,
    ToggleSwitchModule,
    ToolbarModule,
    TooltipModule,
    TreeModule,
    TreeSelectModule,
    TreeTableModule,
    CardModule,
    RippleModule,
    StyleClassModule,
    FloatLabelModule,
    IconFieldModule,
    InputIconModule,
    AutoFocusModule,
    OverlayBadgeModule,
  ],
  providers: [],
})
export class PrimeImportsModule {}
```

### Test Change PrimeNg in /src/app/app.ts
```bash
//Remove
import { ButtonModule } from 'primeng/button'

//Remove
imports: [ButtonModule]

//Add
import { PrimeImportsModule } from './prime-imports';

//Add
imports: [PrimeImportsModule]
```

### Test Run PrimeNg
```bash
ng serve --open
```


## Related

Here are some related projects
- [Angular](https://angular.dev/overview)
- [HyperUI](https://hyperui.dev/)
- [Prime Ng](https://primeng.org/)



















