import axios from 'axios';
export const api=axios.create({baseURL:'/api/v1',withCredentials:true,withXSRFToken:false,headers:{'Content-Type':'application/json'}});
export async function csrf(){const {data}=await api.get('/auth/csrf');api.defaults.headers.common['X-XSRF-TOKEN']=data.token;}
export function errorMessage(error:unknown){if(axios.isAxiosError(error))return error.response?.data?.message??(error.response?.status===409?'Kayıt güncel değil. Yenileyip tekrar deneyin.':'İstek tamamlanamadı.');return 'Beklenmeyen bir hata oluştu.';}
export type User={username:string;fullName:string;roles:string[]};
export type Ticket={id:number;ticketNumber:string;title:string;description:string;category:string;priority:'P1'|'P2'|'P3';status:string;creator:string;assignee?:string;createdAt:string;slaDueAt:string;version:number;slaBreached:boolean};
export type Page<T>={content:T[];totalElements:number;number:number;size:number};
