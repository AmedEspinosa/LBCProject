import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ViewArtworkClient from "../api/viewArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onGetAll', 'renderArtwork', 'renderArtworkList'], this);
        this.dataStore = new DataStore();
        this.dataStoreList = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('view-artwork-form').addEventListener('submit', this.onGet);
        document.getElementById('view-all-artwork-form').addEventListener('submit', this.onGetAll);

            this.client = new ViewArtworkClient();

            this.dataStore.addChangeListener(this.renderArtwork);
            this.dataStoreList.addChangeListener(this.renderArtworkList);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderArtwork() {
    
        let resultArea = document.getElementById("view-artwork-form");

        const artwork = this.dataStore.get("artwork");

        if (artwork) {
            resultArea.innerHTML = `
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
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

        async renderArtworkList() {

            let resultArea = document.getElementById("view-all-artwork-form");

            const artworks = this.dataStoreList.get("artworks");

            resultArea.innerHTML = ''

            for (const artwork of artworks) {
                resultArea.innerHTML += `
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
                    <div>-------------------------------------------------------</div>
                `
            }
        }

    // Event Handlers --------------------------------------------------------------------------------------------------
    async onGetAll(event) {
        event.preventDefault();

        const artworks = await this.client.getAllArtwork(this.errorHandler)

        this.dataStoreList.set("artworks", artworks);
    }

    async onGet(event) {
        event.preventDefault();

        let id = document.getElementById("view-artwork-id").value;
        this.dataStore.set("artwork", null);

        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);

        if (result) {
            this.showMessage(`Found artwork ${result.title}!`)
        } else {
            this.errorHandler("No artwork found with given ID!");
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
