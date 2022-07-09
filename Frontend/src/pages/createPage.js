import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CreateArtworkClient from "../api/createArtworkClient1";

/**
 * Logic needed for the view playlist page of the website.
 */
class CreatePage extends BaseClass {

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
        this.dataStore.addChangeListener(this.renderExample);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("create-artwork-form");

        const artwork = this.dataStore.get("createdArtwork");

        if (artwork) {
            resultArea.innerHTML = `
                <div>Name: ${artwork.artistName}</div>
                <div>Title: ${artwork.title}</div>
                <div>DateCreated: ${artwork.dateCreated}</div>
                <div>Height: ${artwork.height}</div>
                <div>Width: ${artwork.width}</div>
                <div>ForSale: ${artwork.forSale}</div>
                <div>Price: ${artwork.price}</div>
                <div>ID: ${artwork.id}</div>
                <div>Date Posted: ${artwork.datePosted}</div>
                <div>Is Sold: ${artwork.sold}</div>
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

        let artistName = document.getElementById("create-artwork-artist").value;
        let title = document.getElementById("create-artwork-title").value;
        let dateCreated = document.getElementById("create-artwork-dateCreated").value;
        let height = document.getElementById("create-artwork-height").value;
        let width = document.getElementById("create-artwork-width").value;
        let forSale = document.getElementById("create-artwork-isForSale").value;
        let price = document.getElementById("create-artwork-price").value;

        const createdArtwork = await this.client.addNewArtwork(artistName, title, dateCreated,
            height, width, forSale, price);
        this.dataStore.set("createdArtwork", createdArtwork);

        if (createdArtwork) {
            this.showMessage(`Created ${createdArtwork.artistName}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createPage = new CreatePage();
    createPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
