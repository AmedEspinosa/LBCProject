import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import DeleteArtworkClient from "../api/deleteArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class DeletePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onDelete', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('delete-artwork-form').addEventListener('submit', this.onDelete);

        this.client = new DeleteArtworkClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    async renderExample() {

        let resultArea = document.getElementById("delete-artwork-form");

        const artwork = this.dataStore.get("artwork");

        if (artwork) {
            resultArea.innerHTML = `
                <div>****DELETED****</div>
                <div>ID: ${artwork.id}</div>
                <div>Date Posted: ${artwork.datePosted}</div>
                <div>Artist Name: ${artwork.artistName}</div>
                <div>Title: ${artwork.title}</div>
                <div>Date Created: ${artwork.dateCreated}</div>
                <div>Height: ${artwork.height}</div>
                <div>Width: ${artwork.width}</div>
                <div>Is Sold: ${artwork.sold}</div>
                <div>Is For Sale: ${artwork.forSale}</div>
                <div>Price: ${artwork.price}</div>
                <div>****DELETED****</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    async onDelete(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        let id = document.getElementById("delete-artwork-id").value;
        this.dataStore.set("artwork", null);
        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);
        if (result) {
            this.showMessage(`Found artwork ${result.title}!`);
            this.client.deleteArtwork(id);
            this.showMessage(`Deleted artwork ${result.title}`);
        } else {
            this.errorHandler("No artwork found with given ID!");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const deletePage = new DeletePage();
    deletePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
