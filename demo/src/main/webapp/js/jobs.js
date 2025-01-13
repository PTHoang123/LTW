document.addEventListener("DOMContentLoaded", function () {
  const currentUserId = window.currentUserId || null;

  // Edit job function
  window.editJob = function (id) {
    window.location.href = `editJob?id=${id}`;
  };

  // View job detail function
  window.viewJobDetail = function (id) {
    window.location.href = `jobDetail?id=${id}`;
  };

  // Fetch jobs function
  async function fetchJobs() {
    try {
      const searchTerm = document.getElementById("search").value;
      const jobType = document.getElementById("filter-type").value;

      const params = new URLSearchParams({
        search: searchTerm,
        type: jobType,
      });

      const response = await fetch(`getAllJobs?${params.toString()}`, {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const jobs = await response.json();
      const tbody = document.querySelector(".jobs-table tbody");
      tbody.innerHTML = "";

      if (!jobs || jobs.length === 0) {
        tbody.innerHTML = `
                  <tr>
                      <td colspan="7" style="text-align: center;">No jobs found</td>
                  </tr>`;
        return;
      }

      jobs.forEach((job) => {
        const showActions = parseInt(job.createdBy) === parseInt(currentUserId);
        const actionButtons = showActions
          ? `
                  <button class="edit-btn" onclick="editJob(${job.id})">Edit</button>
                  <button class="delete-btn" onclick="deleteJob(${job.id})">Delete</button>
              `
          : "";

        tbody.innerHTML += `
                  <tr>
                      <td><a href="javascript:void(0)" onclick="viewJobDetail(${
                        job.id
                      })">${job.jobTitle}</a></td>
                      <td>${job.companyName}</td>
                      <td>${job.location}</td>
                      <td>${job.jobType}</td>
                      <td>${job.salary}</td>
                      <td>${actionButtons}</td>
                      <td>${job.createdByUsername || "Anonymous"}</td>
                  </tr>`;
      });
    } catch (error) {
      console.error("Error fetching jobs:", error);
      document.querySelector(".jobs-table tbody").innerHTML = `
              <tr>
                  <td colspan="7" style="text-align: center; color: red;">
                      Error loading jobs. Please try again.
                  </td>
              </tr>`;
    }
  }

  // Initial load
  fetchJobs();

  // Search input with debounce
  let searchTimeout;
  document.getElementById("search").addEventListener("input", function () {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(fetchJobs, 300);
  });

  // Search button
  document
    .querySelector(".search-bar button")
    .addEventListener("click", fetchJobs);

  // Filter change
  document.getElementById("filter-type").addEventListener("change", fetchJobs);

  // Delete job
  window.deleteJob = function (id) {
    if (!confirm("Are you sure you want to delete this job?")) return;

    fetch(`deleteJob?id=${id}`, {
      method: "DELETE",
      credentials: "same-origin",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          fetchJobs();
        } else if (response.status === 401) {
          window.location.href = "login.jsp";
        } else if (response.status === 403) {
          alert("You do not have permission to delete this job");
        } else {
          throw new Error("Failed to delete job");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Error deleting job. Please try again.");
      });
  };
});
