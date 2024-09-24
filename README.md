# ***BatiConstructionsX***
## Overview
BatiConstructionsX is a Java Console application tailored for construction professionals specializing in kitchen construction and renovation. This powerful tool streamlines the process of estimating kitchen project costs by accurately calculating expenses related to materials and labor. Designed with user-friendliness in mind, it enables effective project management and enhances decision-making for construction teams.

## Table of Contents
[Features](#features)
[Installation](#installation)
[Usage](#usage)
[License](#license)
## Features
### 1. Project Management
Client Association: Easily add and manage clients associated with each project.
Component Management: Organize materials and labor efficiently.
Cost Estimation: Link estimates with projects to forecast costs before commencing work.
Project Attributes:
Name: Identifies the project.
Profit Margin: Percentage added to the total cost.
Total Cost: Computed cost for the project.
Status: Current state of the project (In Progress, Completed, Canceled).
### 2. Component Management
Material Costs: Manage various attributes for materials, including:
Name
Unit Cost
Quantity
Component Type (Material or Labor)
VAT Rate
Transport Cost
Quality Coefficient
Labor Costs: Calculate labor expenses based on:
Hourly Rate
Hours Worked
Worker Productivity
### 3. Client Management
Client Registration: Capture essential client details, accommodating both professional and private statuses, which can influence discounts or tax rates.
Client Attributes:
Name
Address
Phone Number
Is Professional (Boolean flag for distinguishing between professional and private clients)
### 4. Estimate Generation
Pre-Work Estimates: Generate detailed estimates before starting work, incorporating costs for materials, labor, equipment, and applicable taxes.
Estimate Attributes:
Estimated Amount: Total projected cost for the project.
Issue Date: Date the estimate was generated.
Validity Date: Date until the estimate remains valid.
Accepted: Boolean flag indicating if the estimate has been accepted by the client.
### 5. Cost Calculation
Total Cost Integration: Seamlessly combine costs from materials and labor to derive the overall project cost.
Profit Margin Application: Factor in the profit margin to calculate the final project cost.
Tax and Discount Management: Include applicable taxes (VAT) and discounts in the total cost.
### 6. Project Detail Display
Comprehensive Overview: Present full project details, including client information, component breakdown, and total costs.
Detailed Cost Summary: Generate a summary outlining total costs, including labor, materials, taxes, and profit margin.
### 7. Reporting Feature (New)
Export Reports: Generate and export detailed reports of project estimates and costs for record-keeping or client presentation.
### 8. User Authentication (New)
Secure Access: Implement user authentication to ensure that only authorized personnel can access sensitive project information.
## Installation
To get started with BatiConstructionsX, clone the repository using the command below:

git clone https://github.com/LoussalMohammed/Bati_Cuisine_LoussalMohammed.git
