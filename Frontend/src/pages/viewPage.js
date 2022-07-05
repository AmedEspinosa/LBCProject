import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ViewArtworkClient from "../api/viewArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('view-artwork-form').addEventListener('submit', this.onGet);

        this.client = new ViewArtworkClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------
//      TODO : RE-CODE RENDERING
    async renderExample() {
//        let resultArea = document.getElementById("result-info");
//
//        const example = this.dataStore.get("example");
//
//        if (example) {
//            resultArea.innerHTML = `
//                <div>ID: ${example.id}</div>
//                <div>Name: ${example.name}</div>
//            `
//        } else {
//            resultArea.innerHTML = "No Item";
//        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------
// TODO : RE-WRITE TO VIEW ALL ARTWORK
    async onGet(event) {
//        // Prevent the page from refreshing on form submit
//        event.preventDefault();
//
//        let id = document.getElementById("id-field").value;
//        this.dataStore.set("example", null);
//
//        let result = await this.client.getExample(id, this.errorHandler);
//        this.dataStore.set("example", result);
//        if (result) {
//            this.showMessage(`Got ${result.name}!`)
//        } else {
//            this.errorHandler("Error doing GET!  Try again...");
//        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
