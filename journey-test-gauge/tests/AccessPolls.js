/* globals gauge*/
"use strict";
const {openBrowser, write, closeBrowser, goto, click, screenshot, text, button, client, $, highlight, evaluate} = require('taiko');
const expect = require('chai').expect;

const path = require('path');

beforeSuite(async () => {
    await openBrowser({args: ['--no-sandbox', '--disable-setuid-sandbox'], headless: true})
});

afterSuite(async () => {
    await closeBrowser();
});

gauge.customScreenshotWriter = async function () {
    const screenshotFilePath = path.join(process.env['gauge_screenshots_dir'], `screenshot-${process.hrtime.bigint()}.png`);
    await screenshot({ path: screenshotFilePath });
    return path.basename(screenshotFilePath);
};

step("Go to home page", async () => {
    await goto("http://localhost:8080/");
    expect(await text("Anonymous Polls").exists()).to.be.true;
});
