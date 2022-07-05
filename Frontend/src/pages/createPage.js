import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CreateArtworkClient from "../api/createArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class createPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('create-artwork-form').addEventListener('submit', this.onGet);
        this.client = new CreateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample)
        this.onCreate();
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
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("artwork", null);

        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("artwork", null);

        let datePosted = document.getElementById("update-datePosted-field").value;
        this.dataStore.set("datePosted", datePosted);

        let name = document.getElementById("create-artwork-artist").value;
        this.dataStore.set("artistName", name);

        let title = document.getElementById("create-artwork-title").value;
        this.dataStore.set("title", title);

        let dateCreated = document.getElementById("create-artwork-dateCreated").value;
        this.dataStore.set("dateCreated", dateCreated);

        let height = document.getElementById("create-artwork-height").value;
        this.dataStore.set("height", height);

        let width = document.getElementById("create-artwork-width").value;
        this.dataStore.set("width", width);

        let isSoldYes = document.getElementById("create-artwork-isSoldYes").value;
        this.dataStore.set("isSold", isSoldYes);

        let isSoldNo = document.getElementById("create-artwork-isSoldNo").value;
        this.dataStore.set("isSold", isSoldNo);

        let isForSaleYes = document.getElementById("create-artwork-isForSaleYes").value;
        this.dataStore.set("isForSale", isForSaleYes);

        let isForSaleNo = document.getElementById("create-artwork-isForSaleNo").value;
        this.dataStore.set("isForSale", isForSaleNo);

        let price = document.getElementById("create-artwork-price").value;
        this.dataStore.set("price", price);

        const createdArtwork = await this.client.createExample(datePosted, name, title, dateCreated,
         height, width, isSoldYes, isSoldNo, isForSaleYes, isForSaleNo, price, this.errorHandler);
        this.dataStore.set("artwork", createdArtwork);

        if (createdExample) {
            this.showMessage(`Created ${createdArtwork.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createPage1 = new createPage();
    createPage1.mount();
};

window.addEventListener('DOMContentLoaded', main);
