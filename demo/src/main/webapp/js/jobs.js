document.addEventListener("DOMContentLoaded", function () {
  const currentUserId = window.currentUserId || null;

  function fetchJobs() {
    const searchTerm = document.getElementById("search").value;
    const jobType = document.getElementById("filter-type").value;

    const params = new URLSearchParams({
      search: searchTerm,
      type: jobType,
    });
    const url = `/demo/getAllJobs?${params.toString()}`;
    console.log("Fetch URL:", url); // Debug
    fetch(`/demo/getAllJobs?${params.toString()}`)
      .then((response) => {
        console.log("Response status:", response.status); // Debug
        return response.json();
      })
      .then((jobs) => {
        console.log("Received jobs:", jobs);
        const tbody = document.querySelector(".jobs-table tbody");
        tbody.innerHTML = "";

        jobs.forEach((job) => {
          const showActions = job.createdBy === currentUserId;
          const actionButtons = showActions
            ? `
                        <button class="edit-btn" onclick="editJob(${job.id})">Edit</button>
                        <button class="delete-btn" onclick="deleteJob(${job.id})">Delete</button>
                    `
            : "";

          tbody.innerHTML += `
                        <tr>
                            <td>${job.jobTitle}</td>
                            <td>${job.companyName}</td>
                            <td>${job.location}</td>
                            <td>${job.jobType}</td>
                            <td>${job.salary}</td>
                            <td>${actionButtons}</td>
                        </tr>
                    `;
        });
      })
      .catch((error) => console.error("Error:", error));
  }

  // Initial load
  fetchJobs();

  // Search button click
  document
    .querySelector(".search-bar button")
    .addEventListener("click", fetchJobs);

  // Search input enter key
  document.getElementById("search").addEventListener("keyup", function (event) {
    if (event.key === "Enter") {
      fetchJobs();
    }
  });

  // Job type change
  document.getElementById("filter-type").addEventListener("change", fetchJobs);
});
