(function () {
    const TOAST_DURATION = 2600;

    function createToastContainer() {
        let container = document.getElementById("solemate-toast-container");

        if (!container) {
            container = document.createElement("div");
            container.id = "solemate-toast-container";

            Object.assign(container.style, {
                position: "fixed",
                top: "24px",
                right: "24px",
                zIndex: "99999",
                display: "flex",
                flexDirection: "column",
                gap: "10px",
                pointerEvents: "none"
            });

            document.body.appendChild(container);
        }

        return container;
    }

    window.showToast = function (message, type = "success") {
        const container = createToastContainer();

        const toast = document.createElement("div");

        const isError = type === "error";

        Object.assign(toast.style, {
            minWidth: "280px",
            maxWidth: "380px",
            padding: "15px 18px",
            background: "#111111",
            color: "#ffffff",
            borderRadius: "12px",
            boxShadow: "0 12px 35px rgba(0, 0, 0, 0.18)",
            fontFamily: "Arial, sans-serif",
            fontSize: "14px",
            lineHeight: "1.4",
            display: "flex",
            alignItems: "center",
            gap: "10px",
            opacity: "0",
            transform: "translateY(-10px)",
            transition: "opacity 0.22s ease, transform 0.22s ease",
            pointerEvents: "auto"
        });

        const icon = document.createElement("span");
        icon.textContent = isError ? "!" : "✓";

        Object.assign(icon.style, {
            width: "24px",
            height: "24px",
            minWidth: "24px",
            borderRadius: "50%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            background: isError ? "#b42318" : "#2f7d32",
            color: "#ffffff",
            fontWeight: "700",
            fontSize: "13px"
        });

        const text = document.createElement("span");
        text.textContent = message;

        toast.appendChild(icon);
        toast.appendChild(text);
        container.appendChild(toast);

        requestAnimationFrame(() => {
            toast.style.opacity = "1";
            toast.style.transform = "translateY(0)";
        });

        setTimeout(() => {
            toast.style.opacity = "0";
            toast.style.transform = "translateY(-10px)";

            setTimeout(() => {
                toast.remove();

                if (container.children.length === 0) {
                    container.remove();
                }
            }, 220);
        }, TOAST_DURATION);
    };
})();