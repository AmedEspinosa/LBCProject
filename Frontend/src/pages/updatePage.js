import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UpdateArtworkClient from "../api/updateArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UpdatePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onUpdate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('update-artwork-form').addEventListener('submit', this.onUpdate);
        this.client = new UpdateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById('update-artwork-form');

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
                <div>IsSold: ${artwork.sold}</div>
                <div>IsForSale: ${artwork.forSale}</div>
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

        let id = document.getElementById("update-id-field").value;
        let datePosted = document.getElementById("update-datePosted-field").value;
        let artistName = document.getElementById("update-artistName-field").value;
        let title = document.getElementById("update-title-field").value;
        let dateCreated = document.getElementById("update-dateCreated-field").value;
        let height = document.getElementById("update-height-field").value;
        let width = document.getElementById("update-width-field").value;
        let sold = document.getElementById("update-artwork-sold").value;
        let forSale = document.getElementById("update-artwork-forSale").value;
        let price = document.getElementById("update-artwork-price").value;

        let original = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", original);
        if (original) {
            let update = await this.client.updateArtwork(
                id, datePosted, artistName, title, dateCreated, height, width, sold, forSale, price);
            this.dataStore.set("artwork", update);
            this.showMessage(`Updated ${update.title}!`);
        } else {
            this.errorHandler("No artwork found with given ID!");
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
