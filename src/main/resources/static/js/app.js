/* =========================================================
   SOLEMATE
   Frontend Application
   ========================================================= */

const API_URL = "/api/products";

let products = [];
let cart = JSON.parse(localStorage.getItem("solemateCart")) || [];


/* =========================================================
   DOM ELEMENTS
   ========================================================= */

const productGrid = document.getElementById("productGrid");
const searchInput = document.getElementById("searchInput");
const categoryFilter = document.getElementById("categoryFilter");
const sortSelect = document.getElementById("sortSelect");

const cartButton = document.getElementById("cartButton");
const cartCount = document.getElementById("cartCount");
const cartSidebar = document.getElementById("cartSidebar");
const closeCartButton = document.getElementById("closeCart");
const overlay = document.getElementById("overlay");

const cartItems = document.getElementById("cartItems");
const cartTotal = document.getElementById("cartTotal");

const toast = document.getElementById("toast");
const toastTitle = document.getElementById("toastTitle");
const toastMessage = document.getElementById("toastMessage");

const collectionCount = document.getElementById("collectionCount");
const heroProductCount = document.getElementById("heroProductCount");
const heroProductImage = document.getElementById("heroProductImage");
const clearFilters = document.getElementById("clearFilters");
const searchButton = document.getElementById("searchButton");


/* =========================================================
   INITIALIZE
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
    updateCartUI();
    setupEventListeners();

    const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.get("cart") === "open") {
        openCart();

        window.history.replaceState(
            {},
            document.title,
            window.location.pathname
        );
    }
});


/* =========================================================
   LOAD PRODUCTS FROM BACKEND
   ========================================================= */

async function loadProducts() {
    showLoading();

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to load products");
        }

        products = await response.json();

        setupCategoryFilter();
        updateCollectionMeta();
        setHeroImage();
        displayProducts(products);

        /*
         * Refresh cart UI after products are loaded.
         * This allows older cart items to find their
         * current image from the backend product list.
         */
        updateCartUI();

    } catch (error) {
        console.error("Product loading error:", error);
        showError();
    }
}


/* =========================================================
   PRODUCT IMAGE
   ========================================================= */

/*
 * IMPORTANT:
 * The backend database is now the single source of truth
 * for product images.
 *
 * product.imageUrl comes directly from:
 *
 * MySQL → Spring Boot → /api/products → JavaScript
 */
function getProductImage(product) {

    if (product?.imageUrl) {
        return product.imageUrl;
    }

    /*
     * Older cart items may not contain imageUrl.
     * Look up the complete product from the backend data.
     */
    const fullProduct = products.find(
        item => Number(item.id) === Number(product?.id)
    );

    return fullProduct?.imageUrl || "";
}


/*
 * Handles broken or missing image URLs.
 */
function handleImageError(imageElement) {

    if (!imageElement) {
        return;
    }

    imageElement.style.display = "none";

    const parent = imageElement.parentElement;

    if (parent && !parent.querySelector(".image-fallback")) {

        const fallback = document.createElement("div");

        fallback.className = "image-fallback";

        fallback.innerHTML = `
            <span>IMAGE UNAVAILABLE</span>
        `;

        parent.appendChild(fallback);
    }
}


/* =========================================================
   HERO IMAGE
   ========================================================= */

function setHeroImage() {

    if (!heroProductImage || products.length === 0) {
        return;
    }

    const firstProduct = products[0];
    const image = getProductImage(firstProduct);

    if (image) {
        heroProductImage.src = image;
        heroProductImage.alt =
            `${firstProduct.brand} ${firstProduct.name}`;

        heroProductImage.onerror = () => {
            handleImageError(heroProductImage);
        };
    }
}


/* =========================================================
   PRODUCT DISPLAY
   ========================================================= */

function displayProducts(productList) {

    if (!productGrid) {
        return;
    }

    if (productList.length === 0) {

        productGrid.innerHTML = "";

        document
            .getElementById("emptyState")
            ?.removeAttribute("hidden");

        if (collectionCount) {
            collectionCount.textContent = "0 pairs";
        }

        return;
    }

    document
        .getElementById("emptyState")
        ?.setAttribute("hidden", "");

    productGrid.innerHTML = productList
    .map((product, index) => createProductCard(product, index))
    .join("");

    if (collectionCount) {

        collectionCount.textContent =
            `${productList.length} ${
                productList.length === 1
                    ? "pair"
                    : "pairs"
            }`;
    }
}


/* =========================================================
   PRODUCT CARD
   ========================================================= */

function createProductCard(product, index)  {

    const image = getProductImage(product);

    const imageMarkup = image
        ? `
            <img
                src="${escapeHTML(image)}"
                alt="${escapeHTML(product.brand)} ${escapeHTML(product.name)}"
                loading="lazy"
                onerror="handleImageError(this)"
            >
        `
        : `
            <div class="image-fallback">
                <span>IMAGE UNAVAILABLE</span>
            </div>
        `;

    return `
        <article
            class="product-card"
            data-product-id="${product.id}"
            onclick="openProduct(${product.id})"
        >

            <div class="product-image">

                <span class="product-number">
                    ${String(index + 1).padStart(2, "0")}
                </span>

                <span class="product-category-tag">
                    ${escapeHTML(product.category)}
                </span>

                ${imageMarkup}

                <button
                    class="quick-add"
                    onclick="event.stopPropagation(); addToCart(${product.id})"
                    aria-label="Add ${escapeHTML(product.name)} to cart"
                >
                    <span>+</span>
                </button>

            </div>


            <div class="product-info">

                <div class="product-brand">
                    ${escapeHTML(product.brand)}
                </div>

                <h3 class="product-name">
                    ${escapeHTML(product.name)}
                </h3>

                <div class="product-bottom">

                    <span class="product-price">
                        ₹${Number(product.price).toLocaleString("en-IN")}
                    </span>

                    <span class="product-cta">
                        View pair <span>↗</span>
                    </span>

                </div>

            </div>

        </article>
    `;
}


/* =========================================================
   OPEN PRODUCT
   ========================================================= */

function openProduct(productId) {

    window.location.href =
        `/product.html?id=${productId}`;
}


/* =========================================================
   SEARCH / FILTER / SORT
   ========================================================= */

function applyFilters() {

    const searchTerm =
        searchInput?.value.toLowerCase().trim() || "";

    const selectedCategory =
        categoryFilter?.value || "all";

    const selectedSort =
        sortSelect?.value || "default";


    let filteredProducts = products.filter(product => {

        const name =
            String(product.name || "").toLowerCase();

        const brand =
            String(product.brand || "").toLowerCase();

        const category =
            String(product.category || "").toLowerCase();


        const matchesSearch =
            name.includes(searchTerm) ||
            brand.includes(searchTerm) ||
            category.includes(searchTerm);


        const matchesCategory =
            selectedCategory === "all" ||
            product.category === selectedCategory;


        return matchesSearch && matchesCategory;
    });


    /* ---------- SORT ---------- */

    if (selectedSort === "price-low") {

        filteredProducts.sort(
            (a, b) => a.price - b.price
        );
    }


    if (selectedSort === "price-high") {

        filteredProducts.sort(
            (a, b) => b.price - a.price
        );
    }


    if (selectedSort === "name") {

        filteredProducts.sort(
            (a, b) =>
                String(a.name)
                    .localeCompare(String(b.name))
        );
    }


    displayProducts(filteredProducts);
}


/* =========================================================
   CATEGORY FILTER
   ========================================================= */

function setupCategoryFilter() {

    if (!categoryFilter) {
        return;
    }


    const categories = [
        ...new Set(
            products
                .map(product => product.category)
                .filter(Boolean)
        )
    ].sort();


    categoryFilter.innerHTML = `
        <option value="all">
            All categories
        </option>

        ${categories
            .map(category => `
                <option value="${escapeHTML(category)}">
                    ${escapeHTML(category)}
                </option>
            `)
            .join("")}
    `;
}


/* =========================================================
   COLLECTION META
   ========================================================= */

function updateCollectionMeta() {

    const count = products.length;


    if (heroProductCount) {

        heroProductCount.textContent =
            `${count}+`;
    }


    if (collectionCount) {

        collectionCount.textContent =
            `${count} ${
                count === 1
                    ? "pair"
                    : "pairs"
            }`;
    }
}


/* =========================================================
   CART — ADD
   ========================================================= */

function addToCart(productId) {

    const product =
        products.find(
            item => Number(item.id) === Number(productId)
        );


    if (!product) {
        return;
    }


    /*
     * Basic stock protection.
     */
    if (
        product.stock !== null &&
        product.stock !== undefined &&
        product.stock <= 0
    ) {

        showToast(
            "Out of stock",
            `${product.name} is currently unavailable.`
        );

        return;
    }


    const existingItem =
        cart.find(
            item => Number(item.id) === Number(productId)
        );


    if (existingItem) {

        /*
         * Do not allow cart quantity to exceed
         * available inventory.
         */
        if (
            product.stock &&
            existingItem.quantity >= product.stock
        ) {

            showToast(
                "Stock limit reached",
                `Only ${product.stock} pairs are available.`
            );

            return;
        }


        existingItem.quantity += 1;

        /*
         * Keep image information updated.
         */
        existingItem.imageUrl =
            product.imageUrl || existingItem.imageUrl;

    } else {

        cart.push({

            id: product.id,

            name: product.name,

            brand: product.brand,

            price: product.price,

            category: product.category,

            imageUrl: product.imageUrl,

            quantity: 1
        });
    }


    saveCart();
    updateCartUI();


    showToast(
        "Added to bag",
        `${product.name} is now in your shopping bag.`
    );
}


/* =========================================================
   CART — REMOVE
   ========================================================= */

function removeFromCart(productId, size = "") {

    cart = cart.filter(
        item => !(
            Number(item.id) === Number(productId) &&
            (item.size || "") === size
        )
    );

    saveCart();
    updateCartUI();
}
/* =========================================================
   CART — UPDATE QUANTITY
   ========================================================= */
function changeQuantity(productId, change, size = "") {

    const item =
        cart.find(
            product =>
                Number(product.id) === Number(productId) &&
                (product.size || "") === size
        );

    if (!item) {
        return;
    }

    const backendProduct =
        products.find(
            product =>
                Number(product.id) === Number(productId)
        );

    item.quantity += change;

    if (item.quantity <= 0) {

        removeFromCart(productId, size);

        return;
    }

    /*
     * Prevent quantity from exceeding inventory.
     */
    if (
    backendProduct &&
    backendProduct.stock &&
    item.quantity > backendProduct.stock
) {

    item.quantity = backendProduct.stock;

    showToast(
        "Stock limit reached",
        `Only ${backendProduct.stock} pairs are available.`
    );
}

    saveCart();
    updateCartUI();
}

/* =========================================================
   CART — UI
   ========================================================= */

function updateCartUI() {

    if (!cartItems) {
        return;
    }


    if (cart.length === 0) {

        cartItems.innerHTML = `
            <div class="cart-empty">

                <div class="cart-empty-icon">
                    ○
                </div>

                <h3>
                    Your bag is empty
                </h3>

                <p>
                    Add a pair and it will appear here.
                </p>

            </div>
        `;

    } else {

        cartItems.innerHTML = cart
            .map(item => createCartItem(item))
            .join("");
    }


    const totalItems =
        cart.reduce(
            (total, item) =>
                total + item.quantity,
            0
        );


    const totalPrice =
        cart.reduce(
            (total, item) =>
                total +
                (item.price * item.quantity),
            0
        );


    if (cartCount) {

        cartCount.textContent =
            totalItems;
    }


    if (cartTotal) {

        cartTotal.textContent =
            `₹${totalPrice.toLocaleString("en-IN")}`;
    }
}


/* =========================================================
   CART ITEM
   ========================================================= */

 function createCartItem(item) {

    const image =
        getProductImage(item);

    const sizeText =
        item.size
            ? `Size ${escapeHTML(item.size)}`
            : "Size not selected";

    const encodedSize =
        encodeURIComponent(item.size || "");

    const imageMarkup = image
        ? `
            <img
                src="${escapeHTML(image)}"
                alt="${escapeHTML(item.name)}"
                onerror="handleImageError(this)"
            >
        `
        : `
            <div class="image-fallback">
                <span>IMAGE</span>
            </div>
        `;

    return `
        <div class="cart-item">

            <div class="cart-item-image">
                ${imageMarkup}
            </div>

            <div class="cart-item-content">

                <span class="cart-item-brand">
                    ${escapeHTML(item.brand)}
                </span>

                <strong class="cart-item-name">
                    ${escapeHTML(item.name)}
                </strong>

                <span class="cart-item-price">
                    ₹${Number(item.price).toLocaleString("en-IN")}
                </span>

                <span class="cart-item-size">
                    ${sizeText}
                </span>

                <div class="quantity-control">

                    <button
                        onclick="changeQuantity(${item.id}, -1, decodeURIComponent('${encodedSize}'))"
                        aria-label="Decrease quantity"
                    >
                        −
                    </button>

                    <span>
                        ${item.quantity}
                    </span>

                    <button
                        onclick="changeQuantity(${item.id}, 1, decodeURIComponent('${encodedSize}'))"
                        aria-label="Increase quantity"
                    >
                        +
                    </button>

                </div>

            </div>

            <button
                class="cart-remove"
                onclick="removeFromCart(${item.id}, decodeURIComponent('${encodedSize}'))"
                aria-label="Remove ${escapeHTML(item.name)}"
            >
                ×
            </button>

        </div>
    `;
}
/* =========================================================
   LOCAL STORAGE
   ========================================================= */

function saveCart() {

    localStorage.setItem(
        "solemateCart",
        JSON.stringify(cart)
    );
}


/* =========================================================
   CART DRAWER
   ========================================================= */

function openCart() {

    cartSidebar?.classList.add("open");

    overlay?.classList.add("active");

    document.body.classList.add("cart-open");
}


function closeCart() {

    cartSidebar?.classList.remove("open");

    overlay?.classList.remove("active");

    document.body.classList.remove("cart-open");
}


/* =========================================================
   EVENT LISTENERS
   ========================================================= */

function setupEventListeners() {

    searchInput?.addEventListener(
        "input",
        applyFilters
    );


    categoryFilter?.addEventListener(
        "change",
        applyFilters
    );


    sortSelect?.addEventListener(
        "change",
        applyFilters
    );


    cartButton?.addEventListener(
        "click",
        openCart
    );


    closeCartButton?.addEventListener(
        "click",
        closeCart
    );


    overlay?.addEventListener(
        "click",
        closeCart
    );


    searchButton?.addEventListener(
        "click",
        () => {

            document
                .getElementById("shop")
                ?.scrollIntoView({
                    behavior: "smooth"
                });


            setTimeout(
                () => searchInput?.focus(),
                450
            );
        }
    );


    clearFilters?.addEventListener(
        "click",
        () => {

            if (searchInput) {
                searchInput.value = "";
            }


            if (categoryFilter) {
                categoryFilter.value = "all";
            }


            if (sortSelect) {
                sortSelect.value = "default";
            }


            applyFilters();
        }
    );


    document.addEventListener(
        "keydown",
        event => {

            if (event.key === "Escape") {
                closeCart();
            }


            if (
                event.key === "/" &&
                document.activeElement !== searchInput
            ) {

                event.preventDefault();

                searchInput?.focus();
            }
        }
    );
}


/* =========================================================
   LOADING / ERROR
   ========================================================= */

function showLoading() {

    if (!productGrid) {
        return;
    }


    document
        .getElementById("emptyState")
        ?.setAttribute("hidden", "");


    productGrid.innerHTML = `
        <div class="loading-state">

            <div class="loading-spinner"></div>

            <p>
                Loading the collection...
            </p>

        </div>
    `;
}


function showError() {

    if (!productGrid) {
        return;
    }


    productGrid.innerHTML = `
        <div class="loading-state error-state">

            <div class="error-symbol">
                !
            </div>

            <h3>
                Collection unavailable
            </h3>

            <p>
                Make sure the Spring Boot backend is running.
            </p>

            <button
                class="button button-dark"
                onclick="loadProducts()"
            >
                Try again
            </button>

        </div>
    `;
}


/* =========================================================
   TOAST
   ========================================================= */

function showToast(title, message) {

    if (!toast) {
        return;
    }


    if (toastTitle) {

        toastTitle.textContent =
            title;
    }


    if (toastMessage) {

        toastMessage.textContent =
            message;
    }


    toast.classList.add("show");


    clearTimeout(
        window.soleMateToastTimer
    );


    window.soleMateToastTimer =
        setTimeout(
            () => {

                toast.classList.remove("show");

            },
            2800
        );
}


/* =========================================================
   HTML SAFETY
   ========================================================= */

function escapeHTML(value) {

    return String(value)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );
}
// Open cart automatically when redirected from another page
const urlParams = new URLSearchParams(window.location.search);

if (urlParams.get("cart") === "open") {
    setTimeout(() => {
        openCart();
    }, 100);
}