import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ViewArtworkClient from "../api/viewArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onGetAll', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('view-artwork-form').addEventListener('submit', this.onGet);
//        document.getElementById('view-all-artwork-form').addEventListener('submit', this.onGetAll);

            this.client = new ViewArtworkClient();

            this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
    
        let resultArea = document.getElementById("view-artwork-form");

        const artwork = this.dataStore.get("artwork");

        if (artwork) {
            resultArea.innerHTML = `
                <div>ID: ${artwork.id}</div>
                <div>Artist Name: ${artwork.artistName}</div>
                <div>Title: ${artwork.title}</div>
                <div>Date Created: ${artwork.dateCreated}</div>
                <div>Date Posted: ${artwork.datePosted}</div>
                <div>Height: ${artwork.height}</div>
                <div>Width: ${artwork.width}</div>
                <div>Is Sold: ${artwork.isSold}</div>
                <div>Is For Sale: ${artwork.isForSale}</div>
                <div>Price: ${artwork.price}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------
// TODO : VIEW ALL ARTWORK
    async onGetAll(event) {
        const artworks = await this.client.getAllArtwork(this.errorHandler)

        if (artworks && artworks.length > 0) {
            for (const artwork of artworks) {
                await this.getArtwork(artwork.id);
            }
        }
        this.dataStore.set("artworks", artworks);
    }

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("view-artwork-id").value;
        this.dataStore.set("artwork", null);

        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);

        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPage = new ViewPage();
    viewPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
