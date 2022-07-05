import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CreateArtworkClient from "../api/createArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class createPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('create-artwork-form').addEventListener('submit', this.onCreate);
        this.client = new CreateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("create-artwork-form");

        const artwork = this.dataStore.getState("createdArtwork");

        if (artwork) {
            resultArea.innerHTML = `
                <div>Name: ${artwork.name}</div>
                <div>Title: ${artwork.title}</div>
                <div>DateCreated: ${artwork.dateCreated}</div>
                <div>Height: ${artwork.height}</div>
                <div>Width: ${artwork.width}</div>
                <div>IsForSale: ${artwork.isForSale}</div>
                <div>Price: ${artwork.price}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

//    async onGet(event) {
//        // Prevent the page from refreshing on form submit
//        event.preventDefault();
//
////        let id = document.getElementById('create-artwork-form').value;
////        this.dataStore.set("id", null);
//
//        let result = await this.client.getArtwork(id, this.errorHandler);
//        this.dataStore.set("id", result);
//        if (result) {
//            this.showMessage(`Got ${result.id}!`)
//        } else {
//            this.errorHandler("Error doing GET!  Try again...");
//        }
//    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        //this.dataStore.setState(null);


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

        //not sure if I might have to change this option?
        let isForSaleYes = document.getElementById("create-artwork-isForSaleYes").value;
        this.dataStore.set("isForSale", isForSaleYes);

        let isForSaleNo = document.getElementById("create-artwork-isForSaleNo").value;
        this.dataStore.set("isForSale", isForSaleNo);

        let price = document.getElementById("create-artwork-price").value;
        this.dataStore.set("price", price);

        if(isForSaleNo) {
        const createdArtwork = await this.client.post(name, title, dateCreated,
                 height, width, isForSaleNo, price, this.errorHandler);
        } else {
        const createdArtwork = await this.client.post(name, title, dateCreated,
         height, width, isForSaleYes, price, this.errorHandler);
         }
        //this.dataStore.set("artwork", createdArtwork);
        this.dataStore.setState(createdArtwork);

        if (createdArtwork) {
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
