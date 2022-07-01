import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class updatePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet','onUpdate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('update-id-form').addEventListener('submit', this.onGet);
        this.client = new UpdateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample);
        this.onUpdate();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async onGet() {
        event.preventDefault();

         let result = await this.client.getElementById(this.errorHandler);
         this.dataStore.set("id", result);
    }

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const artwork = this.dataStore.get("artwork");

        if (artwork) {
            resultArea.innerHTML = `
                <div>ID: ${artwork.id}</div>
                <div>DatePosted: ${artwork.datePosted}</div>
                <div>Name: ${artwork.artistName}</div>

                <div>Title: ${artwork.title}</div>
                <div>DateCreated: ${artwork.dateCreated}</div>
                <div>Height: ${artwork.height}</div>
                <div>Width: ${artwork.width}</div>
                <div>IsSold: ${artwork.isSold}</div>
                <div>IsForSale: ${artwork.isForSale}</div>
                <div>Price: ${artwork.price}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let datePosted = document.getElementById("update-datePosted-field").value;
        this.dataStore.set("artwork", null);

        let name = document.getElementById("update-name-field").value;
        this.dataStore.set("artwork", null);

        let title = document.getElementById("update-title-field").value;
        this.dataStore.set("artwork", null);

        let dateCreated = document.getElementById("update-dateCreated-field").value;
        this.dataStore.set("artwork", null);

        let height = document.getElementById("update-height-field").value;
        this.dataStore.set("artwork", null);

        let width = document.getElementById("update-width-field").value;
        this.dataStore.set("artwork", null);

        let isSoldYes = document.getElementById("update-artwork-isSoldYes").value;
        this.dataStore.set("artwork", null);

        let isSoldNo = document.getElementById("update-artwork-isSoldNo").value;
        this.dataStore.set("artwork", null);

        let isForSaleYes = document.getElementById("update-artwork-isForSaleYes").value;
        this.dataStore.set("artwork", null);

        let isForSaleNo = document.getElementById("update-artwork-isForSaleNo").value;
        this.dataStore.set("artwork", null);

        let price = document.getElementById("update-artwork-price").value;
        this.dataStore.set("artwork", null);


        let result = await this.client.getExample(id, this.errorHandler);
        this.dataStore.set("example", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let name = document.getElementById("create-name-field").value;

        const createdExample = await this.client.createExample(name, this.errorHandler);
        this.dataStore.set("example", createdExample);

        if (createdExample) {
            this.showMessage(`Created ${createdExample.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updatePage1 = new updatePage();
    updatePage1.mount();
};

window.addEventListener('DOMContentLoaded', main);
