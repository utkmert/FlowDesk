import '@testing-library/jest-dom/vitest';
Object.defineProperty(window,'matchMedia',{writable:true,value:(query:string)=>({matches:false,media:query,onchange:null,addListener:()=>{},removeListener:()=>{},addEventListener:()=>{},removeEventListener:()=>{},dispatchEvent:()=>false})});
const computed=window.getComputedStyle.bind(window);window.getComputedStyle=((element:Element)=>computed(element)) as typeof window.getComputedStyle;
