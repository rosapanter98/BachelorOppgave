document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('.nav');
    const currentLocation = window.location.pathname; // Use pathname for the whole URL path, hash for just the hash

    // Function to update active link based on the current URL
    function updateActiveLink() {
        links.forEach(link => {
            if (link.pathname === currentLocation || link.hash === currentLocation) { // Check against pathname or hash
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        });
    }

    // Update active link on page load
    updateActiveLink();

    // Ensure active class is updated on clicking other links
    links.forEach(link => {
        link.addEventListener('click', function() {
            setTimeout(updateActiveLink, 0); // Update after the URL has changed
        });
    });

    
});

