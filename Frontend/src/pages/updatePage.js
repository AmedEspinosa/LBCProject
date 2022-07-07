import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UpdateArtworkClient from "../api/updateArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UpdatePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet','onUpdate', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('update-id-form').addEventListener('submit', this.onGet);
        this.client = new UpdateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

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

    async onGet(event) {
        event.preventDefault();

         let result = await this.client.getElementById(this.errorHandler);
         this.dataStore.set("id", result);
    }

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let datePosted = document.getElementById("update-datePosted-field").value;
        this.dataStore.set("datePosted", datePosted);

        let name = document.getElementById("update-name-field").value;
        this.dataStore.set("artistName", name);

        let title = document.getElementById("update-title-field").value;
        this.dataStore.set("title", title);

        let dateCreated = document.getElementById("update-dateCreated-field").value;
        this.dataStore.set("dateCreated", dateCreated);

        let height = document.getElementById("update-height-field").value;
        this.dataStore.set("height", height);

        let width = document.getElementById("update-width-field").value;
        this.dataStore.set("width", width);

        let isSoldYes = document.getElementById("update-artwork-isSoldYes").value;
        this.dataStore.set("isSoldYes", isSoldYes);

        let isSoldNo = document.getElementById("update-artwork-isSoldNo").value;
        this.dataStore.set("isSoldNo", isSoldNo);

        let isForSaleYes = document.getElementById("update-artwork-isForSaleYes").value;
        this.dataStore.set("isForSaleYes", isForSaleYes);

        let isForSaleNo = document.getElementById("update-artwork-isForSaleNo").value;
        this.dataStore.set("isForSaleNo", isForSaleNo);

        let price = document.getElementById("update-artwork-price").value;
        this.dataStore.set("price", price);


        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);
        if (result) {
            this.showMessage(`Updated ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("artwork", null);

        let name = document.getElementById("create-name-field").value;

        const createdExample = await this.client.createExample(name, this.errorHandler);
        this.dataStore.set("artwork", createdExample);

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
    const updatePage = new UpdatePage();
    updatePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
