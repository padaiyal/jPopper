<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<div align="center">
  <h1 align="center">jPopper</h1>
  <p align="center">
    A parent for Java maven projects with best practices and commonly used checks enforced.
    <br />
    <a href="https://github.com/padaiyal/jPopper/issues/new/choose">Report Bug/Request Feature</a>
  </p>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url] <br>
![Maven build - Ubuntu latest](https://github.com/padaiyal/jPopper/workflows/Maven%20build%20-%20Ubuntu%20latest/badge.svg?branch=main)
![Maven build - Windows latest](https://github.com/padaiyal/jPopper/workflows/Maven%20build%20-%20Windows%20latest/badge.svg?branch=main)
![Maven build - MacOS latest](https://github.com/padaiyal/jPopper/workflows/Maven%20build%20-%20MacOS%20latest/badge.svg?branch=main)
![Publish to GitHub packages](https://github.com/padaiyal/jPopper/workflows/Publish%20to%20GitHub%20packages/badge.svg)
</div>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
This project is to serve as a parent maven project to enforce the following good practices and
provide commonly used functionality:
 - Checkstyle enforcement as per the Google java styling guide.
 - PMD static code analysis to enforce good practices.
 - Java code coverage check enforcement.

<!-- USAGE -->
## Usage
This project is only meant to be used as a parent maven project. <br>
To do so:
 1. Add this repository as a git submodule to the child project repository.
 2. In the pom.xml of the child repository, add the following lines:
    ```
    <parent>
       <groupId>org.padaiyal</groupId>
       <artifactId>popper</artifactId>
       <version>2021.01.06</version>
       <relativePath>jPopper</relativePath>
    </parent>
    ```
    Replace the contents of the relativePath tag to the actual relative path of the submodule with 
    respect to the child project's pom.xml. 
 3. To use the existing GitHub action [Publish to GitHub packages](https://github.com/padaiyal/jPopper/blob/main/.github/workflows/package_publish.yml), the distributionManagement section also needs to be configured in the child project's pom.xml.
    ```
    <distributionManagement>
        <repository>
            <id>github</id>
            <name>Child project MVN package deployment</name>
            <url>https://maven.pkg.github.com/<organization_or_user>/<repository_name></url>
        </repository>
    </distributionManagement>
    ```
    NOTE: Ensure that the child project's artifact ID follows the [maven naming conventions](https://maven.apache.org/guides/mini/guide-naming-conventions.html). Else, GitHub packages would reject the upload with a `HTTP 422: Unprocessable Entity` error.

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/padaiyal/jPopper/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project.
2. Create your branch. (`git checkout -b contribution/AmazingContribution`)
3. Commit your changes. (`git commit -m 'Add some AmazingContribution'`)
4. Push to the branch. (`git push origin contribution/AmazingContribution`)
5. Open a Pull Request.


<!-- LICENSE -->
## License
Distributed under the Apache License. See [`LICENSE`](https://github.com/padaiyal/jPopper/blob/main/LICENSE) for more information.


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/padaiyal/jPopper.svg?style=for-the-badge
[contributors-url]: https://github.com/padaiyal/jPopper/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/padaiyal/jPopper.svg?style=for-the-badge
[forks-url]: https://github.com/padaiyal/jPopper/graphs/network/members
[stars-shield]: https://img.shields.io/github/stars/padaiyal/jPopper.svg?style=for-the-badge
[stars-url]: https://github.com/padaiyal/jPopper/stargazers
[issues-shield]: https://img.shields.io/github/issues/padaiyal/jPopper.svg?style=for-the-badge
[issues-url]: https://github.com/padaiyal/jPopper/issues
[license-shield]: https://img.shields.io/github/license/padaiyal/jPopper.svg?style=for-the-badge
[license-url]: https://github.com/padaiyal/jPopper/blob/master/LICENSE
[product-screenshot]: images/screenshot.png
